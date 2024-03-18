import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorUI extends JFrame {
    protected final JTextField displayField; // Display field for showing calculations and results
    public CalculatorUI() {
        //Constructor for CalculatorUI.
        //Sets up the calculator's UI components and layout.
        super("Calculator");

        // Configuring the display field
        displayField = new JTextField("", 16);
        displayField.setFont(new Font("default", Font.BOLD, 18));
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.LEFT);
        displayField.setBackground(Color.WHITE);
        displayField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));

        // Set up the button panel with a grid layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 5, 5)); // 6x4 grid with 5px paddings
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        //labels for the buttons
        String[] buttonStrings = {
                "sin", "cos", "(", ")",
                "AC", "^2", "SQRT","/",
                "7", "8", "9", "X",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", "DEL", ".", "=",

        };

        // Creating and adding buttons to the panel
        for (String buttonString : buttonStrings) {
            JButton button = new JButton(buttonString);
            if ("AC".equals(buttonString) || "^2".equals(buttonString) || "SQRT".equals(buttonString)) {
                button.setBackground(Color.WHITE);
            }
            else if (buttonString.matches("[0-9]") || ".".equals(buttonString)|| "DEL".equals(buttonString)) {
                button.setBackground(Color.GRAY);

            }
            else if ("sin".equals(buttonString) || "cos".equals(buttonString)||"tan".equals(buttonString)|| "(".equals(buttonString)|| ")".equals(buttonString)){
                button.setBackground(Color.orange);
            }
            else {
                button.setBackground(Color.YELLOW);
            }
            if ("=".equals(buttonString) || "X".equals(buttonString)|| "-".equals(buttonString) || "/".equals(buttonString)|| "+".equals(buttonString)) {
                button.setBackground(Color.ORANGE);
            }
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
            button.setFont(new Font("Thoma", Font.BOLD, 14));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        // Add components to the frame
        setLayout(new BorderLayout(5, 5));
        add(displayField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Configure the frame
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Inner class to handle button click events.
     */

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if ("=".equals(command)) {
                try {
                    String result = evaluate(displayField.getText());
                    displayField.setText(result);
                } catch (Exception ex) {
                    displayField.setText("Error");
                }
            } else if ("AC".equals(command)) {
                displayField.setText("");
            } else if ("DEL".equals(command)) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    displayField.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if("X".equals(command)||"-".equals(command)||"+".equals(command)||"/".equals(command)) {
                displayField.setText(displayField.getText()+" "+ command+" ");
            }else
                displayField.setText(displayField.getText()+ command);
        }
    }


    private String evaluate(String expression) {
        try {
            // handle sqrt() and ^2
            expression = Preprocess.preprocessExpression(expression);

            // Finally, pass the simplified expression for evaluation
            double result = ArithmeticEvaluator.evaluate(expression);
            return String.format("%.2f", result); // Formats the result to two decimal places
        } catch (Exception e) {
            e.printStackTrace(); // Consider printing the stack trace for debugging.
            return "Error";
        }
    }

}