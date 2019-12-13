import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;

import javax.swing.JButton;
import java.awt.Cursor;

import java.awt.GridLayout;
import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfaceGraphique extends JFrame{
	 public  InterfaceGraphique() {
		JFrame fenetre = new JFrame();
		fenetre.setResizable(false);
		fenetre.setSize(new Dimension(1080, 720));
		fenetre.setVisible(true);
		fenetre.setTitle("Robot Turtles");
		fenetre.setName("fenetre");
		fenetre.setLocationRelativeTo(null);
		fenetre.getContentPane().setLayout(null);
		
		JLayeredPane panelPrincipal = new JLayeredPane();
		panelPrincipal.setBounds(15, 16, 1044, 648);
		fenetre.getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		
		
		JPanel panelTableau = new JPanel();
		panelTableau.setBounds(263, 64, 494, 403);
		panelPrincipal.add(panelTableau);
		panelTableau.setLayout(new GridLayout(8,8));
		
		for(int i=0;i<8;i++) {
			for(int j =0; j<8;j++) {
				JPanel square = new JPanel(new BorderLayout());
				panelTableau.add(square);
				if((i%2)==0) {
					if((j%2==0)) {
						square.setBackground(Color.blue);
					}
					else {
						square.setBackground(Color.white);
					}
				}
				else {
					if((j%2==0)) {
						square.setBackground(Color.white);
					}
					else {
						square.setBackground(Color.blue);
					}
				}
			}
		}
		/*for (int i = 0; i < 64; i++) {
			  JPanel square = new JPanel(new BorderLayout());
			  panelTableau.add(square);
			  int row = (int)(i / 8) % 2;
			  if (row == 0)
			  square.setBackground( i % 2 == 0 ? Color.blue : Color.white );
			  else
			  square.setBackground( i % 2 == 0 ? Color.white : Color.blue );
		}*/
		
		JButton boutonValiderCarte = new JButton("Valider Carte");
		boutonValiderCarte.setBounds(529, 586, 150, 29);
		panelPrincipal.add(boutonValiderCarte);
		
		JButton boutonFini = new JButton("J'ai fini");
		boutonFini.setBounds(826, 587, 115, 29);
		panelPrincipal.add(boutonFini);
		boutonFini.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		
		final JButton carte1 = new JButton("carte");
		carte1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carte1.setBackground(Color.blue);
			}
		});
		carte1.setBounds(15, 478, 84, 154);
		panelPrincipal.add(carte1);
		
		JButton carte2 = new JButton("New button");
		carte2.setBounds(97, 478, 84, 154);
		panelPrincipal.add(carte2);
		
		JButton carte3 = new JButton("New button");
		carte3.setBounds(178, 478, 84, 154);
		panelPrincipal.add(carte3);
		
		JButton carte4 = new JButton("New button");
		carte4.setBounds(259, 478, 84, 154);
		panelPrincipal.add(carte4);
		
		JButton carte5 = new JButton("New button");
		carte5.setBounds(341, 478, 84, 154);
		panelPrincipal.add(carte5);
		
		
		
		JLabel titreConteneur = new JLabel("Bondour, bienvenue dans le wobot tuhtle");
		titreConteneur.setBounds(366, 16, 299, 20);
		panelPrincipal.add(titreConteneur);
		
		
		
		
		
		System.out.println("bla");
		
		
	}
}
