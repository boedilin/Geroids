package ch.zhaw.soe.psit3.geroids.test.unit;

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

public class TestCollisionHandler {
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
	public void setUp() throws Exception {
		geroids = new ArrayList<Geroid>();
		figureNoCollision = new Figure(new Position(50,5));
		figureCollision = new Figure(new Position(10,2));
		projectiles = new ArrayList<Projectile>();
		geroidNoCollision = new Geroid(new Position(1,1,3,3), new Movement(1,1));
		geroidCollision = new Geroid(new Position(9,1,2,2), new Movement(1,1));
		geroidOutofGamefield = new Geroid(new Position(1,1001), new Movement(1,1));
		projectileOutofGamefield = new Projectile(new Position(1,-2));
		projectileNoCollision = new Projectile(new Position(5,5));
		projectileCollision = new Projectile(new Position(10,1));
		geroids.add(geroidNoCollision);
		projectiles.add(projectileNoCollision);
		collisionHandler = new CollisionHandler(1000);
	}
	
	@Test(expected = NullPointerException.class)  
	public void testCollisionHandler() throws Exception{
		new CollisionHandler(-5);
	}
	
	@Test(expected = NullPointerException.class)  
	public void testCheckIfGeroidIsCollidingWithProjectileNull(){
		collisionHandler.checkIfGeroidIsCollidingWithProjectile(geroidNull, projectileNull);
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
		assertEquals(false, collisionHandler.checkAllGeroidsCollisionWithFigure(geroids, figureNoCollision));
	}
	
	@Test  
	public void testCheckIfGeroidIsCollidingWithProjectileNoCollision(){
		assertEquals(false, collisionHandler.checkIfGeroidIsCollidingWithProjectile(geroidNoCollision, projectileNoCollision));
	}

	@Test
	public void testCheckAllGeroidsCollisionWithFigureCollision(){
		geroids.add(geroidCollision);
		assertEquals(true, collisionHandler.checkAllGeroidsCollisionWithFigure(geroids, figureCollision));
	}

	@Test  
	public void testCheckIfGeroidIsCollidingWithProjectileCollision(){
		assertEquals(true, collisionHandler.checkIfGeroidIsCollidingWithProjectile(geroidCollision, projectileCollision));
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
