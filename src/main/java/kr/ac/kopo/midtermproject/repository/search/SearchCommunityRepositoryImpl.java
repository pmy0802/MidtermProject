package kr.ac.kopo.midtermproject.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import kr.ac.kopo.midtermproject.entity.Community;
import kr.ac.kopo.midtermproject.entity.QCommunity;
import kr.ac.kopo.midtermproject.entity.QMember;
import kr.ac.kopo.midtermproject.entity.QReply;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchCommunityRepositoryImpl extends QuerydslRepositorySupport implements SearchCommunityRepository {

    public SearchCommunityRepositoryImpl(){
        super(Community.class);
    }

    @Override
    public Community search1() {
        log.info("search1() 메소드 호출됨");
        QCommunity community = QCommunity.community;
        QReply reply = QReply.reply;
        QMember member = QMember.member;



        JPQLQuery<Community> jpqlQuery = from(community);
        jpqlQuery.leftJoin(member).on(community.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.community.eq(community));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(community, member.email, reply.count());
        tuple.groupBy(community, member, reply);

        log.info("=================================");
        log.info(jpqlQuery);
        log.info("=================================");

        // JPQL 실행 방법
        List<Tuple> result = tuple.fetch();

        log.info("=================================");
        log.info(result);
        log.info("=================================");


        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage() 메소드 호출됨");

        QCommunity community = QCommunity.community;
        QReply reply = QReply.reply;
        QMember member = QMember.member;



        JPQLQuery<Community> jpqlQuery = from(community);
        jpqlQuery.leftJoin(member).on(community.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.community.eq(community));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(community, member, reply.count());
//        tuple.groupBy(board, member, reply);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression =community.bno.gt(0L);

        booleanBuilder.and(expression);

        if(type != null){
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for(String t:typeArr){
                switch (t){
                    case "t":
                        conditionBuilder.or(community.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(community.content.contains(keyword));
                        break;
                }//end switch
            }//end for
            booleanBuilder.and(conditionBuilder);
        }//end if

        tuple.where(booleanBuilder);
        // Sorting(Order by)
        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC:Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Community.class, "community");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(community, member);

        // 페이지 처리에 필요한 값(offset, limit) 설정
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info(result);

        long count = tuple.fetchCount();

        log.info("실행된 행의 개수: " + count);

        return new PageImpl<Object[]>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }
}
