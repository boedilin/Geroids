package ch.zhaw.soe.psit3.geroids.test.integration;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.CollisionHandler;
import ch.zhaw.soe.psit3.geroids.domain.Figure;
import ch.zhaw.soe.psit3.geroids.domain.Geroid;
import ch.zhaw.soe.psit3.geroids.domain.Movement;
import ch.zhaw.soe.psit3.geroids.domain.Position;
import ch.zhaw.soe.psit3.geroids.domain.Projectile;

public class TestIntegrationCollisionHandler {
	
	private CollisionHandler collisionHandler;
	private ArrayList<Geroid> geroids;
	private Figure figureNoCollision;
	private Figure figureCollision;
	private ArrayList<Projectile> projectiles;
	private Geroid geroidNull;
	private Geroid geroidOutofGamefield;
	private Geroid geroidNoCollision;
	private Geroid geroidCollision;
	private Projectile projectileNull;
	private Projectile projectileOutofGamefield;
	private Projectile projectileNoCollision;
	private Projectile projectileCollision;

	@Before
	public void setUp() {
		geroids = new ArrayList<Geroid>();
		figureNoCollision = new Figure(new Position(50,5));
		figureCollision = new Figure(new Position(10,2));
		projectiles = new ArrayList<Projectile>();
		geroidNoCollision = new Geroid(1, new Position(1,1,3,3), new Movement(1,1));
		geroidCollision = new Geroid(2, new Position(9,1,2,2), new Movement(1,1));
		geroidOutofGamefield = new Geroid(3, new Position(1,1001), new Movement(1,1));
		projectileOutofGamefield = new Projectile(new Position(1,-2));
		projectileNoCollision = new Projectile(new Position(5,5));
		projectileCollision = new Projectile(new Position(70,30));
		geroids.add(geroidNoCollision);
		projectiles.add(projectileNoCollision);
		collisionHandler = new CollisionHandler(1000);
	}
	
	@Test(expected = NullPointerException.class)  
	public void testCheckIfGeroidIsCollidingWithProjectileNull(){
		collisionHandler.checkCollisionWithGeroid(geroidNull, projectileNull.getPosition());
	}
	
	@Test(expected = NullPointerException.class)  
	public void testCheckIfGeroidIsOutOfGamefieldNull(){
		collisionHandler.checkIfGeroidIsOutOfGamefield(geroidNull);
	}
	
	@Test(expected = NullPointerException.class)  
	public void testCheckIfProjectileIsOutOfGamefieldNull(){
		collisionHandler.checkIfProjectileIsOutOfGamefield(projectileNull);
	}
	
	@Test
	public void testCheckAllGeroidsCollisionWithFigureNoCollision(){
		collisionHandler.setFigure(true);
		geroids.add(geroidNoCollision);
		collisionHandler.addGeroidsCollisionPoints(geroids.get(0));
		collisionHandler.generateFigureCollisionPoints(figureNoCollision);
		assertEquals(false, collisionHandler.checkAllGeroidsCollisionWithFigure(figureNoCollision, geroids));
	}
	
	@Test  
	public void testCheckIfGeroidIsCollidingWithProjectileNoCollision(){
		collisionHandler.addGeroidsCollisionPoints(geroidNoCollision);
		assertEquals(false, collisionHandler.checkCollisionWithGeroid(geroidNoCollision, projectileNoCollision.getPosition()));
	}

	@Test
	public void testCheckAllGeroidsCollisionWithFigureCollision(){
		collisionHandler.setFigure(true);
		geroids.add(geroidCollision);
		collisionHandler.addGeroidsCollisionPoints(geroids.get(0));
		collisionHandler.generateFigureCollisionPoints(figureCollision);
		assertEquals(true, collisionHandler.checkAllGeroidsCollisionWithFigure(figureCollision, geroids));
	}

	@Test  
	public void testCheckIfGeroidIsCollidingWithProjectileCollision(){
		collisionHandler.addGeroidsCollisionPoints(geroidCollision);
		assertEquals(true, collisionHandler.checkCollisionWithGeroid(geroidCollision, projectileCollision.getPosition()));
	}
	
	@Test  
	public void testCheckIfGeroidIsOutOfGamefield(){
		assertEquals(true, collisionHandler.checkIfGeroidIsOutOfGamefield(geroidOutofGamefield));
	}
	
	@Test  
	public void testCheckIfGeroidIsInGamefield(){
		assertEquals(false, collisionHandler.checkIfGeroidIsOutOfGamefield(geroidNoCollision));
	}
	
	@Test  
	public void testCheckIfProjectileIsOutOfGamefield(){
		assertEquals(true, collisionHandler.checkIfProjectileIsOutOfGamefield(projectileOutofGamefield));
	}
	
	@Test  
	public void testCheckIfProjectileIsInGamefield(){
		assertEquals(false, collisionHandler.checkIfProjectileIsOutOfGamefield(projectileNoCollision));
	}
}

