package gui;

import javax.swing.*;
import java.awt.*;

public class RelationPanel extends JFrame implements RelationUpdateListener {
	private JTextArea textArea;

	public RelationPanel() {
		setTitle("Relations en temps réel");
		setSize(500, 300); // Définissez la taille selon vos besoins
		initUI();
		setVisible(true);
	}

	private void initUI() {
		textArea = new JTextArea();
		textArea.setEditable(false); // Rendre le JTextArea non éditable
		JScrollPane scrollPane = new JScrollPane(textArea);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	public void updateRelations(String newRelationInfo) {
		SwingUtilities.invokeLater(() -> {
			textArea.append(newRelationInfo + "\n"); // Ajoutez la nouvelle info à la fin
			textArea.setCaretPosition(textArea.getDocument().getLength()); // Défiler vers le bas automatiquement
		});
	}

	@Override
    public void onRelationUpdated(String relationInfo) {
        updateRelations(relationInfo);
    }
}