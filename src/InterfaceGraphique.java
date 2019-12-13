import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.GridLayout;

public class InterfaceGraphique {
	public  InterfaceGraphique() {
		JFrame fenetre = new JFrame();
		fenetre.setResizable(false);
		fenetre.setSize(new Dimension(1080, 720));
		fenetre.setVisible(true);
		fenetre.setTitle("Robot Turtles");
		fenetre.setName("fenetre");
		fenetre.setLocationRelativeTo(null);
		fenetre.getContentPane().setLayout(null);
		
		JLabel titreConteneur = new JLabel("Bondour, bienvenue dans le wobot tuhtle");
		titreConteneur.setBounds(364, 43, 299, 20);
		fenetre.getContentPane().add(titreConteneur);
		
		JButton boutonFini = new JButton("J'ai fini");
		boutonFini.setBounds(871, 624, 115, 29);
		boutonFini.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		fenetre.getContentPane().add(boutonFini);
		
		JButton carte1 = new JButton("carte");
		carte1.setBounds(51, 499, 84, 154);
		fenetre.getContentPane().add(carte1);
		
		JButton carte2 = new JButton("New button");
		carte2.setBounds(133, 499, 84, 154);
		fenetre.getContentPane().add(carte2);
		
		JButton carte3 = new JButton("New button");
		carte3.setBounds(215, 499, 84, 154);
		fenetre.getContentPane().add(carte3);
		
		JButton carte4 = new JButton("New button");
		carte4.setBounds(296, 499, 84, 154);
		fenetre.getContentPane().add(carte4);
		
		JButton carte5 = new JButton("New button");
		carte5.setBounds(378, 499, 84, 154);
		fenetre.getContentPane().add(carte5);
		
		JButton boutonValiderCarte = new JButton("Valider Carte");
		boutonValiderCarte.setBounds(496, 624, 150, 29);
		fenetre.getContentPane().add(boutonValiderCarte);
		
		
		
		
		
	}
}
