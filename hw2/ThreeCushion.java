package hw2;

import api.BallType;
import api.PlayerPosition;
import static api.BallType.*;
import static api.PlayerPosition.*;

/**
 * Class that models the game of three-cushion billiards.
 * 
 * @author chilana
 */
public class ThreeCushion {
	/**
	 * @author chila
	 * cueBall for the second player
	 */
	private BallType cueBall2;
	/**
	 * @author chila
	 * cueball for the first player
	 */
	private BallType cueBall;
	/**
	 * @author chila
	 * the given cue ball when asked
	 */
	private BallType currentCueBall;
	/**
	 * @author chila
	 * the player who won the ability to choose break
	 */
	private PlayerPosition winner;
	
	/**
	 * @author chila
	 * current inningPlayer
	 */
	private PlayerPosition inningPlayer;
	/**
	 * @author chila
	 * current inning at time requested
	 */
	private int currentInning;
	/**
	 * @author chila
	 * if the inning is in progress or not
	 */
	private boolean inningStatus;
	/**
	 * @author chila
	 * if the shot is the break shot or not
	 */
	private boolean breakShot;
	/**
	 * @author chila
	 * points for player a
	 */
	private int pointA;
	/**
	 * @author chila
	 * points for player b
	 */
	private int pointB;
	/**
	 * @author chila
	 * if the shot is in progress or not
	 */
	private boolean shotStatus;
	/**
	 *@author chila
	 * if there is enough cushion hits to get a point
	 */
	private boolean pointable;
	/**
	 * @author chila
	 * if there the cushion hits and the balls have been hit
	 */
	private boolean pointWithBall;
	/**
	 * @author chila
	 * keeps tracks of the cushion hits
	 */
	private int cushHit;
	/**
	 * @author chila
	 * keeps track how many balls hit
	 */
	private int ballTap;
	/**
	 * @author chila
	 * bankshot tracker
	 */
	private boolean bankShot = false;
	/**
	 * @author chila
	 * given points needed to win
	 */
	private int pointsToWin;
	/**
	 * @author chila
	 * keeps track of if the game is over
	 */
	private boolean gameOver = false;

	
	
	/**
	 * @author chila
	 * Creates a new game of three-cushion billiards with a given lag winner and 
	 * the predetermined number of points required to win the game.
	 * @param lagWinner
	 * @param pointsToWin
	 */
	public ThreeCushion(PlayerPosition lagWinner, int pointsToWin) {
		//all the aspect here just intialize the game
		currentInning = 1;
		inningStatus = false;
		breakShot = true;
		this.pointsToWin = pointsToWin;
		winner = lagWinner;
	}
	/**
	 * @author chila
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * <p>
	 * <tt>Player A*: X Player B: Y, Inning: Z</tt>
	 * <p>
	 * The asterisks next to the player's name indicates which player is at the
	 * table this inning. The number after the player's name is their score. Z is
	 * the inning number. Other messages will appear at the end of the string.
	 * 
	 * @return one-line string representation of the game state
	 */
	public String toString() {
		String fmt = "Player A%s: %d, Player B%s: %d, Inning: %d %s%s";
		String playerATurn = "";
		String playerBTurn = "";
		String inningStatus = "";
		String gameStatus = "";
		if (getInningPlayer() == PLAYER_A) {
			playerATurn = "*";
		} else if (getInningPlayer() == PLAYER_B) {
			playerBTurn = "*";
		}
		if (isInningStarted()) {
			inningStatus = "started";
		} else {
			inningStatus = "not started";
		}
		if (isGameOver()) {
			gameStatus = ", game result final";
		}
		return String.format(fmt, playerATurn, getPlayerAScore(), playerBTurn, getPlayerBScore(), getInning(),
				inningStatus, gameStatus);
	}
	/**
	 * @author chila
	 * Indicates the given ball has impacted the given cushion
	 */
	public void cueBallImpactCushion() {
	//dictating if it hit
		cushHit += 1;
		if(cushHit >= 3) {
			pointable = true;
		} else if(cushHit < 3){
			pointable = false;
		}
	// I created this pointable boolean becasue i thought i would be easier to work with
		// if i could assing something to make it be a point
		if(cushHit >= 3 & ballTap == 0) {
			bankShot = true;
		}
		
	}
	
	/**
	 * @author chila
	 * @param ball
	 * Indicates the player's cue ball has struck the given ball
	 */
	public void cueBallStrike(BallType ball) {
	int redCount = 0;
	
	if(!gameOver) {
		if(ball == RED) {
			redCount += 1;
		}		
		if(cushHit < 3 && ballTap == 2) {
			pointable = false;
			endShot();
		}
		if(ballTap == 2 && cushHit == 0) {
			pointable = false;
			endShot();
		}
		if(currentCueBall != ball) {
				ballTap += 1;
				if(redCount > 1) {
					ballTap -= 1;
				}
				if (pointable && ballTap == 2) {
					pointWithBall = true;
					ballTap = 0;
					cushHit = 0;
					//I created the point with ball so I could mainpulate more with both booleans
				}
			}
		}
	}
	/**
	 * @author chila
	 * @param ball
	 * Indicates the cue stick has struck the given ball
	 */
	public void cueStickStrike(BallType ball) {
		//assigning the ball based on who the player is
		if(inningPlayer == PLAYER_B) {
			currentCueBall = cueBall2;
		} else if(inningPlayer == PLAYER_A) {
			currentCueBall = cueBall;
		}
		//its a foul if you hit the wrong ball
		if(ball != currentCueBall) {
			foul();
		}
		if(gameOver) {
			shotStatus = false;
			inningStatus = false;
		} else {
			inningStatus = true;
			shotStatus = true;
		}
	}
	/**
	 * @author chila
	 * Indicates that all balls have stopped motion
	 */
	public void endShot() {
		breakShot = false;
		ballTap = 0;
		cushHit = 0;
		if(shotStatus){
			//if a player doesn't get a point and the shot ends
			//the following should happen
			if(!pointWithBall && inningPlayer == PLAYER_A) {
				currentInning += 1;
				shotStatus = false;
				inningPlayer = PLAYER_B;
				if(inningPlayer == PLAYER_B) {
					currentCueBall = cueBall2;
				}
				inningStatus = false;
			}//if its a vaild point they should get a point
			else if(pointWithBall && inningPlayer == PLAYER_A){
				pointA += 1;
				if(pointA == pointsToWin) {
					gameOver = true;
				}
			}else if(!pointWithBall && inningPlayer == PLAYER_B) {
				inningPlayer = PLAYER_A;
				currentInning += 1;
				shotStatus = false;
				inningStatus = false;
				if(inningPlayer == PLAYER_A) {
					currentCueBall = cueBall;
				}
			}else if(pointWithBall && inningPlayer == PLAYER_B){
				pointB += 1;
				if(pointB == pointsToWin) {
					gameOver = true;
				}
			}
		}
			shotStatus = false;
	}
	/**
	 * @author chila
	 * A foul immediately ends the player's inning, 
	 * even if the current shot has not yet ended.
	 */
	public void foul() {
		inningStatus = false;
		shotStatus = false;
		if(inningPlayer == PLAYER_A) {
			inningPlayer = PLAYER_B;
			if(!gameOver) {
			currentInning += 1;
			}
			if(inningPlayer == PLAYER_B) {
				currentCueBall = cueBall2;
			}
		} else if(inningPlayer == PLAYER_B) {
			inningPlayer = PLAYER_A;
			if(!gameOver) {
				currentInning += 1;
			}
			if(inningPlayer == PLAYER_A) {
				currentCueBall = cueBall;
			}
		}
	}
	/**
	 * @author chila
	 * @return the current cueball
	 */
	public BallType getCueBall() {
		return currentCueBall;
	}
	/**
	 * @author chila
	 * @return current inning
	 */
	public int getInning() {
		return currentInning;
	}
	/**
	 * @author chila
	 * @return current inning player
	 */
	public PlayerPosition getInningPlayer() {
		return inningPlayer;
	}
	/**
	 * @author chila
	 * @return player a score
	 */
	public int getPlayerAScore() {
		return pointA;
	}
	/**
	 * @author chila
	 * @return player b score
	 */
	public int getPlayerBScore() {
		return pointB;
	}
	/**
	 * @author chila
	 * @return if its a bank shot
	 */
	public boolean isBankShot() {
		return bankShot;
	}
	/**
	 * @author chila
	 * @return is a break shot
	 */
	public boolean isBreakShot() {
		return breakShot;
		
	}
	/**
	 * @author chila
	 * @return is the game over
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	/**
	 * @author chila
	 * @return the status of the inning
	 */
	public boolean isInningStarted() {
		return inningStatus;
	}
	/**
	 * @author chila
	 * @return the status of the shot
	 */
	public boolean isShotStarted() {
		return shotStatus;
	}
	/**
	 * @author chila
	 * @param selfBreak if the winner wants to self break
	 * @param cueBall what ball the winner wants
	 * Sets whether the player that won the lag chooses to break (take first shot)
	 *  or chooses the other player to break.
	 */
	public void lagWinnerChooses(boolean selfBreak, BallType cueBall) {
		/*
		 * all of the code here is just assinging the lagWinner the ability to break
		 * so if they don't want to break the other player breaks and then the winner picks a ball
		 * and the other player get the other ball
		 */
		if(winner == PLAYER_A) {
			if(cueBall == WHITE) {
				this.cueBall = cueBall;
				cueBall2 = YELLOW;
			} else if(cueBall == YELLOW) {
				cueBall2 = WHITE;
				this.cueBall = cueBall;
			}
			if(selfBreak) {
				inningPlayer = PLAYER_A;
				currentCueBall = this.cueBall;
			} else {
				inningPlayer = PLAYER_B;
				currentCueBall = cueBall2;
			}
		}
		if(winner == PLAYER_B) {
			if(cueBall == WHITE) {
				cueBall2 = WHITE;
				this.cueBall = YELLOW;
			} else if(cueBall == YELLOW) {
				cueBall2 = YELLOW;
				this.cueBall = WHITE;
			}
			if(selfBreak) {
				inningPlayer = PLAYER_B;
				currentCueBall = cueBall2;
			} else {
				inningPlayer = PLAYER_A;
				currentCueBall = this.cueBall;
			}
			
		}
			
		
	}
	
}
