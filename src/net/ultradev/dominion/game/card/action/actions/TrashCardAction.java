package net.ultradev.dominion.game.card.action.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.ultradev.dominion.game.Turn;
import net.ultradev.dominion.game.card.Card;
import net.ultradev.dominion.game.card.CardManager;
import net.ultradev.dominion.game.card.action.Action;
import net.ultradev.dominion.game.utils.Utils;

public class TrashCardAction extends Action {
	
	public static enum TrashType { CHOOSE_AMOUNT, SPECIFIC_AMOUNT, RANGE };
	
	int amount;
	int min, max;
	TrashType type;
	
	List<Card> restriction;
	
	public TrashCardAction(String identifier, String description) {
		super(identifier, description);
		this.type = TrashType.CHOOSE_AMOUNT;
		this.restriction = new ArrayList<>();
	}
	
	public TrashCardAction(String identifier, String description, int amount) {
		super(identifier, description);
		this.amount = amount;
		this.type = TrashType.SPECIFIC_AMOUNT;
		this.restriction = new ArrayList<>();
	}
	
	public TrashCardAction(String identifier, String description, int min, int max) {
		super(identifier, description);
		this.min = min;
		this.max = max;
		this.type = TrashType.RANGE;
		this.restriction = new ArrayList<>();
	}
	
	public void addRestriction(Card card) {
		restriction.add(card);
	}
	
	public boolean isRestricted() {
		return restriction.size() == 0;
	}
	
	public List<Card> getRestriction() {
		return restriction;
	}

	@Override
	public void play(Turn turn) {
		//TODO trash 'amount' cards
	}
	
	public static Action parse(String identifier, String description, Map<String, String> params, TrashType type) {
		TrashCardAction action = null;
		switch(type) {
			case CHOOSE_AMOUNT:
				action = new TrashCardAction(identifier, description);
				break;
			case RANGE:
				int min = Utils.parseInt(params.get("min"), 0);
				int max = Utils.parseInt(params.get("max"), 4);
				action = new TrashCardAction(identifier, description, min, max);
				break;
			case SPECIFIC_AMOUNT:
			default:
				action = new TrashCardAction(identifier, description);
				break;
		}
		if(params.containsKey("restrict")) {
			String[] toRestrict = params.get("restrict").split(",");
			for(String restrict : toRestrict)
				action.addRestriction(CardManager.get(restrict));
		}
		return action;
	}
	
}