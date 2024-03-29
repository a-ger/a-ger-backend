package com.ireland.ager.product.repository;

import com.ireland.ager.product.dto.response.ProductThumbResponse;
import com.ireland.ager.product.entity.Category;
import com.ireland.ager.product.entity.Product;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ireland.ager.product.entity.QProduct.product;
import static com.ireland.ager.product.entity.QUrl.url1;
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public void addViewCntFromRedis(Long productId, Long addCnt) {
        queryFactory
                .update(product)
                .set(product.productViewCnt, addCnt)
                .where(product.productId.eq(productId))
                .execute();
    }

    @Override
    public Product addViewCnt(Long productId) {
        queryFactory
                .update(product)
                .set(product.productViewCnt, product.productViewCnt.add(1))
                .where(product.productId.eq(productId))
                .execute();
        return queryFactory
                .selectFrom(product)
                .leftJoin(product.urlList, url1)
                .fetchJoin()
                .where(product.productId.eq(productId),product.eq(url1.productId))
                .distinct()
                .fetchOne();
    }
    @Override
    public Slice<ProductThumbResponse> findAllProductPageableOrderByCreatedAtDesc(Category category, String keyword, Pageable pageable) {
        JPAQuery<Product> productQuery = queryFactory
                .selectFrom(product)
                .where(keywordListContains(keyword), categoryEq(category))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1); //limit보다 한 개 더 들고온다.
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(product.getType(), product.getMetadata());
            productQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<ProductThumbResponse> content = new ArrayList<>(ProductThumbResponse.toProductListResponse(productQuery.fetch()));
        boolean hasNext = false;
        //마지막 페이지는 사이즈가 항상 작다.
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<ProductThumbResponse> findSellProductsByAccountId(Long accountId, Pageable pageable) {
        List<Long> ids = queryFactory
                .select(product.productId)
                .from(product)
                .where(product.account.accountId.eq(accountId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new SliceImpl<>(new ArrayList<>(), pageable, true);
        }

        JPAQuery<Product> productQuery = queryFactory
                .selectFrom(product)
                .where(product.productId.in(ids));

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(product.getType(), product.getMetadata());
            productQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<ProductThumbResponse> content = new ArrayList<>(ProductThumbResponse.toProductListResponse(productQuery.fetch()));
        boolean hasNext = false;
        //마지막 페이지는 사이즈가 항상 작다.
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression categoryEq(Category category) {
        return ObjectUtils.isEmpty(category) ? null : product.category.eq(category);
    }

    /*
    private BooleanExpression keywordContains(String keyword) {
        return ObjectUtils.isEmpty(keyword) ? null : product.productName.contains(keyword);
    }
     */

    private BooleanBuilder keywordListContains(String keyword) {
        if(ObjectUtils.isEmpty(keyword)) return null;
        BooleanBuilder builder=new BooleanBuilder();
        String[] splitedKeyword = keyword.split(" ");
        for(String value:splitedKeyword) {
            builder.and(product.productName.contains(value));
        }
        return builder;
    }
}
