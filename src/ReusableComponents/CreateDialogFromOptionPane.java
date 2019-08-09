package ReusableComponents;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CreateDialogFromOptionPane {

	public static void setWarningMsg(String text){
	    Toolkit.getDefaultToolkit().beep();
	    JOptionPane optionPane = new JOptionPane(text,JOptionPane.CLOSED_OPTION);
	    JDialog dialog = optionPane.createDialog("Alert!");
	    dialog.setAlwaysOnTop(true);
	    dialog.setVisible(true);
	}
	
	
}
