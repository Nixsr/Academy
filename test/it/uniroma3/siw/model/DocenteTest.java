package it.uniroma3.siw.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;

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

class DocenteTest {

	Docente docente1 = new Docente();
	Docente docente2 = new Docente();
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
		Query deleteQuery = em.createNativeQuery("DELETE FROM Docente");

		docente1.setNome("Luca");
		docente1.setCognome("Lauria");
		docente1.setDataDiNascita(LocalDate.of(1980, 9, 13));
		docente1.setLuogoDiNascita("Roma");
		docente1.setPartitaIva(75985736493L);

		docente2.setNome("Mario");
		docente2.setCognome("Rossi");
		docente2.setDataDiNascita(LocalDate.of(1975, 3, 7));
		docente2.setLuogoDiNascita("Milano");
		docente2.setPartitaIva(17364839537L);

		tx.begin();
		int deletedRows = deleteQuery.executeUpdate();
		em.persist(docente1);
		em.persist(docente2);
		tx.commit();
	}

	@Test
	void testDynamicQuery() {
		TypedQuery<Docente> selectQuery = em.createQuery("SELECT d FROM Docente d", Docente.class);
		assertEquals(2, selectQuery.getResultList().size() );
		assertEquals(docente1, selectQuery.getResultList().get(0));
		assertEquals(docente2, selectQuery.getResultList().get(1));
		Query deleteQuery = em.createQuery("DELETE FROM Docente d");
		tx.begin();
		int deletedRows = deleteQuery.executeUpdate();
		tx.commit();
		assertEquals(2, deletedRows);
	}

}
