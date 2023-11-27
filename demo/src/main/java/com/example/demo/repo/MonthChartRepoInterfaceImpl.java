package com.example.demo.repo;


import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MonthChartRepoInterfaceImpl implements MonthChartRepoInterface {
    private final EntityManager em;

    public MonthChartRepoInterfaceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Long> getData() {
        class month{
            Long cnt;
        }
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String formatedNow = now.format(formatter);
//        System.out.println(formatedNow);

        List<Long> MonthStatistics = new ArrayList<>();
        for (int i=1; i<=12; i++){
            String stDate = '\'' + formatedNow + '-' + i + '-' + "01" + '\'';

            Optional<Long> any = em.createQuery(
                            "SELECT count(c.id) FROM carNumber c WHERE MONTH("+stDate+") = MONTH(c.timestamp)"
                            , Long.class)
                    .getResultList()
                    .stream().findAny();
            MonthStatistics.add(any.get()); //월별 데이터 입력 (대시보드 차트 구현용)
        }

        return MonthStatistics;
    }
}
