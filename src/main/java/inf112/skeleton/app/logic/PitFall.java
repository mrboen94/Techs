package inf112.skeleton.app.logic;

import java.util.ArrayList;

import inf112.skeleton.app.objects.PlayerToken;

public class PitFall implements IBoardFeature {

	private BoardLogic boardLogic;
	private ArrayList<PlayerToken> playersList;
	private ArrayList<String> playersChecked;
	private String layerName = "Pit";

	public PitFall(BoardLogic boardLogic, ArrayList<PlayerToken> playersList) {
		this.playersList = playersList;
		this.boardLogic = boardLogic;
	}

	@Override
	public void processFeature() {
		for (PlayerToken player : playersList) {
			movePlayerIfPitFall(player);
			// should lose a damagetoken when implemented
		}
	}

	public void movePlayerIfPitFall(PlayerToken player) {
		if (playersChecked.contains(player.getName()))
			return;

		playersChecked.add(player.getName());
		int xPos = player.getXPosition();
		int yPos = player.getYPosition();

		if (boardLogic.cellContainsLayer(xPos, yPos, layerName)) {
			player.moveToLastCheckpoint(); // Moves player to current backup position
			return;
		}
		return;
	}

}
