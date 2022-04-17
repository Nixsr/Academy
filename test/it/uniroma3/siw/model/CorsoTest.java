package it.uniroma3.siw.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CorsoTest {

	Corso corso1 = new Corso();
	Corso corso2 = new Corso();
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static EntityTransaction tx;

	@BeforeAll
	public static void initEntityManager() throws Exception {
		emf = Persistence.createEntityManagerFactory("products-unit-test");
		em = emf.createEntityManager();
	}

	@AfterAll
	public static void closeEntityManager() throws SQLException {
		if (em != null) em.close();
		if (emf != null) emf.close();
	}

	@BeforeEach
	public void initTransaction() {
		tx = em.getTransaction();
		Query deleteQuery = em.createNativeQuery("DELETE FROM Corso");
		
		corso1.setNome("Fisica");
		corso1.setDataDiInizio(LocalDate.of(2022, 10, 1));
		corso1.setDurata(4);

		corso2.setNome("Analisi");
		corso2.setDataDiInizio(LocalDate.of(2022, 10, 3));
		corso1.setDurata(4);

		tx.begin();
		int deletedRows = deleteQuery.executeUpdate();
		em.persist(corso1);
		em.persist(corso2);
		tx.commit();
	}

	@Test
	void testDynamicQuery() {
		TypedQuery<Corso> selectQuery = em.createQuery("SELECT c FROM Corso c", Corso.class);
		assertEquals(2, selectQuery.getResultList().size() );
		assertEquals(corso1, selectQuery.getResultList().get(0));
		assertEquals(corso2, selectQuery.getResultList().get(1));
		Query deleteQuery = em.createQuery("DELETE FROM Corso c");
		tx.begin();
		int deletedRows = deleteQuery.executeUpdate();
		tx.commit();
		assertEquals(2, deletedRows);
	}

}
