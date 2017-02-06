package states;

import java.util.*;

public class StateMachine {
	
	Map<States, RobotState> classes = new HashMap<>();
	public enum States {START, TURN, MOVE, TRACKSHOT, SHOOT, STOP};
	public enum Goals {SHOOT, LINE, NOTHING};
	public enum Pos {FL, FM, FR, BL, BM, BR};
	double currentAng;
	double desiredAng;
	double distance;
	int iteration;
	Goals goal;
	Pos pos;
	States state;
	States prevState;
	Start start = new Start(goal, pos);
	Move move = new Move(goal, pos);
	Turn turn = new Turn(goal, pos);
	Shoot shoot = new Shoot(goal, pos);
	Stop stop = new Stop();
	
	public StateMachine(int mode){
		switch (mode){
			case 0:
				goal = Goals.SHOOT;
				pos = Pos.FL;
				break;
			case 1:
				goal = Goals.SHOOT;
				pos = Pos.FM;
				break;
			case 2:
				goal = Goals.SHOOT;
				pos = Pos.FR;
				break;
			case 3:
				goal = Goals.SHOOT;
				pos = Pos.BL;
				break;
			case 4:
				goal = Goals.SHOOT;
				pos = Pos.BM;
				break;
			case 5:
				goal = Goals.SHOOT;
				pos = Pos.BR;
				break;
			case 6:
				goal = Goals.LINE;
				pos = Pos.FL;
				break;
			case 7:
				goal = Goals.LINE;
				pos = Pos.FM;
				break;
			case 8:
				goal = Goals.LINE;
				pos = Pos.FR;
				break;
			case 9:
				goal = Goals.LINE;
				pos = Pos.BL;
				break;
			case 10:
				goal = Goals.LINE;
				pos = Pos.BM;
				break;
			case 11:
				goal = Goals.LINE;
				pos = Pos.BR;
				break;
			case 12:
				goal = Goals.NOTHING;
				pos = Pos.FL;
				break;
			default:
				goal = Goals.NOTHING;
				pos = Pos.FL;
		}
	}
	
	public void createMap(){
		classes.put(States.START, start);
		classes.put(States.MOVE, move);
		classes.put(States.TURN, turn);
		classes.put(States.SHOOT, shoot);
		classes.put(States.STOP, stop);
	}
	
	public void updateDistance(){
		switch (goal){
			case NOTHING:
				move.distance = 0;
				break;
			case LINE:
				if (move.distance == -1){
					if (pos == Pos.FL || pos == Pos.FR){
						if (iteration == 1){
							move.distance = 5;
						}
					}else if (pos == Pos.FM){
						if (iteration == 2){
							move.distance = 2;
						}
						if (iteration == 4){
							move.distance = 5;
						}
					}
				}
				break;
			case SHOOT:
				if (move.distance == -1){
					if (pos == Pos.FL){
						if (iteration == 2){
							move.distance = 10;
						}
						if (iteration == 4){
							move.distance = 2;
						}
						if (iteration == 6){
							move.distance = 1;
						}
					}else if (pos == Pos.FM){
						if (iteration == 2){
							move.distance = 5;
						}
						if (iteration == 4){
							move.distance = 2;
						}
						if (iteration == 6){
							move.distance = 1;
						}
					}else if (pos == Pos.FR){
						if (iteration == 1){
							move.distance = 5;
						}
						if (iteration == 3){
							move.distance = 1;
						}
					}
				}
				break;
			default:
				move.distance = 0;
		}
	}
	
	public void updateAngles(){
		switch (goal){
			case NOTHING:
				turn.desired = 0;
				break;
			case LINE:
				if (turn.desired == 400){
					if (pos == Pos.FL || pos == Pos.FR){
						turn.desired = 0;
					}else if (pos == Pos.FM){
						if (iteration == 1){
							turn.desired = turn.current + 90;
						}
						if (iteration == 3){
							move.distance = turn.current - 90;
						}
					}
				}
				break;
			case SHOOT:
				if (turn.desired == 400){
					if (pos == Pos.FL || pos == Pos.FM){
						if (iteration == 1){
							turn.desired = turn.current + 90;
						}
						if (iteration == 3){
							turn.desired = turn.current - 90;
						}
						if (iteration == 5){
							turn.desired = turn.current + 90;
						}
					}else if (pos == Pos.FR){
						if (iteration == 2){
							turn.desired = turn.current + 90;
						}
					}
				}
				break;
			default:
				turn.desired = 0;
		}
	}
	
	public void updateIteration(){
		move.iteration = iteration;
		turn.iteration = iteration;
	}
	
	public void run(){
		iteration = 0;
		createMap();
		state = classes.get(States.START).run();
		prevState = state;
		while (state != States.STOP){
			updateIteration();
			updateDistance();
			updateAngles();
			state = classes.get(state).run();
			if (prevState != state){
				iteration++;
			}
			prevState = state;
		}
	}
}
