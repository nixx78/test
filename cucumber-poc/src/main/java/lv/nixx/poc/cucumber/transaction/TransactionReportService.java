package lv.nixx.poc.cucumber.transaction;

import static java.util.stream.Collectors.groupingBy;
import static java.math.BigDecimal.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class TransactionReportService {

	private TransactionDao dao;

	public void setDao(TransactionDao dao) {
		this.dao = dao;
	}

	public TransactionReport createReport(Date dateFrom, Date dateTo, CountBy countField) {
		Collection<Transaction> txns = dao.getTransactions(dateFrom, dateTo);

		Collector<Transaction, ?, BigDecimal> reducingFunction = null;

		if (countField == CountBy.Count) {
			reducingFunction = Collectors.reducing(ZERO, t -> ONE, BigDecimal::add);
		} else if (countField == CountBy.Amount) {
			reducingFunction = Collectors.reducing(ZERO, Transaction::getAmount, BigDecimal::add);
		}

		Map<String, BigDecimal> lst = txns.stream()
				.collect(Collectors.groupingBy(Transaction::getCurrency, reducingFunction));

		TransactionReport transactionReport = new TransactionReport(countField);
		transactionReport.setCurrency(lst);
		transactionReport.setTotalOperationCount(txns.size());

		return transactionReport;
	}

	public Map<String, Map<String, MonthStatistic>> createByMonthReport(Date dateFrom, Date dateTo) {

		Collection<Transaction> txns = dao.getTransactions(dateFrom, dateTo);

		return txns.stream().collect(
				groupingBy(t -> getMonthFromDate(t.getDate()),   
				groupingBy(Transaction::getCurrency, new StaticsticCollector()))
				);
	}

	private String getMonthFromDate(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate.getMonth().toString();
	}

	class StaticsticCollector implements Collector<Transaction, StatisticAcamullator, MonthStatistic> {

		@Override
		public Supplier<StatisticAcamullator> supplier() {
			return StatisticAcamullator::new;
		}

		@Override
		public BiConsumer<StatisticAcamullator, Transaction> accumulator() {
			return (s, t) -> s.increaseByTransaction(t);
		}

		@Override
		public BinaryOperator<StatisticAcamullator> combiner() {
			return (s1, s2) -> s1;
		}

		@Override
		public Set<Characteristics> characteristics() {
			return Collections.emptySet();
		}

		@Override
		public Function<StatisticAcamullator, MonthStatistic> finisher() {
			return t -> new MonthStatistic(t.getCurrency(), t.getTxnCount(), t.getAmount());
		}

	};

	@NoArgsConstructor
	@ToString
	@Getter
	class StatisticAcamullator {
		private String currency;
		private int txnCount = 0;
		private BigDecimal amount = ZERO;

		void increaseByTransaction(Transaction txn) {
			this.currency = txn.getCurrency();
			this.txnCount++;
			this.amount = this.amount.add(Optional.ofNullable(txn.getAmount()).orElse(ZERO));
		}
	}

}
