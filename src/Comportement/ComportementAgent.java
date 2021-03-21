package Comportement;

import Agent.Agent;
import Modele.Maze;

public abstract class ComportementAgent {
	public abstract boolean getAction(Agent agent,Maze maze);
}
