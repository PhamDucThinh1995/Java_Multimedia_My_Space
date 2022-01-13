import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;
import java.util.Calendar;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

//public class MainWindow extends JFrame implements ActionListener{
    /*Create button on the interface*/
public class MainWindow extends JFrame {

    private JButton Digital_Clock, Countdown_Clock, Analog_Clock;
    /*Grouping on the interface*/
    private JPanel panel1, panel2;
    /*Receive the ContentPane of JFrame*/
    private Container cont;
    /*Function for initializing*/
    public MainWindow(String s)
    {
        super(s);
        /*Using the ContentPane for put the visual object*/
        cont = this.getContentPane();
        /*Create the content on the interface*/
        JLabel StudentName = new JLabel("Student: PHAM Duc Thinh");
        JLabel IDStudent = new JLabel("ID Student: 12108404");
        JLabel Requirement = new JLabel("SUBJECT: ANALOGIC AND DIGITAL CLOCK");
        /*Panel1 content these infos above*/
        panel1 = new JPanel();
        /*Set the Layout include 3 rows and 1 column*/
        panel1.setLayout(new GridLayout(3,1));
        /*Put the elements in the panel 1*/
        panel1.add(StudentName);
        panel1.add(IDStudent);
        panel1.add(Requirement);
        /*Create 3 buttons: one for Countdown Clock, one for Digital Clock
        * and the last one for Analog Clock*/
        Digital_Clock = new JButton("DIGITAL CLOCK");
        Digital_Clock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame clockdigital = new TextClockWindow();// TO DO CHECK HERE
                clockdigital.setVisible(true);
                clockdigital.pack();
            }
        });
        // Add action for button
        Countdown_Clock = new JButton("COUNTDOWN CLOCK");
        Countdown_Clock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame cl = new TimerClass();           // cl abbreviation for countdown clock
                cl.setVisible(true);
                cl.setTitle("COUNTDOWN CLOCK");
                cl.pack();
            }
        });
        Analog_Clock = new JButton("ANALOG CLOCK"); //TO DO CHECK HERE
        Analog_Clock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame ac = new Clock();
                ac.setVisible(true);                    // ac abbreviation for analog clock
                ac.setTitle("ANALOG CLOCK");
                ac.pack();
                ac.setSize(300, 300);
            }
        });
        /*Panel2 include 3 buttons*/
        panel2 = new JPanel();
        panel2.add(Analog_Clock);
        panel2.add(Digital_Clock);
        panel2.add(Countdown_Clock);


        /*Put 2 panels in the same ContentPane*/
        cont.add(panel1);
        cont.add(panel2,"South");

//        Countdown_Clock.addActionListener(this);
//        Digital_Clock.addActionListener(this);
        /*Etablish the dimension to show*/
        this.pack();
        this.setVisible(true);
    }
    /*Coding for DIGITAL CLOCK*/
    class TextClockWindow extends JFrame{   //from class JFrame extend class TExtClockWindow
        private JTextField timeField;
        public TextClockWindow() {
            timeField = new JTextField(10);
            timeField.setFont(new Font("sansserif", Font.PLAIN, 48));

            Container content = this.getContentPane();
            content.setLayout(new FlowLayout());
            content.add(timeField);

            this.setTitle("DIGITAL CLOCK");
            this.pack();

            javax.swing.Timer t = new javax.swing.Timer(1000,
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                            Calendar calendar = new GregorianCalendar();
                            String am_pm;


                            Calendar now = Calendar.getInstance();
                            int h = now.get(Calendar.HOUR_OF_DAY);
                            int m = now.get(Calendar.MINUTE);
                            int s = now.get(Calendar.SECOND);


                            if (calendar.get(Calendar.AM_PM) == 0) {
                                am_pm = "AM";
                            } else {
                                am_pm = "PM";

                            }   // Code to Determine whether the time is AM or PM

                            timeField.setText("" + h + ":" + m + ":" + s + " " + am_pm);
                            timeField.setHorizontalAlignment(JTextField.CENTER);
                            // Center the text
                            timeField.getCaret().setVisible(false);
                            // Hide the Cursor in JTextField
                        }
                    });
            t.start();
        }
    }
    /*Coding for COUNTDOWN CLOCK*/
    class TimerClass extends JFrame{
        private int hours;
        private int minutes;
        private int seconds;
        private JLabel timerLabel;
        private Color originalColor;
        private Timer secTimer;
        private boolean started = false;
        private int initSec, initMin, initHr;
        private JButton startButton;
        private JButton stopButton;
        private JButton resetButton;
        private JButton addButton;
        private JButton newButton;


        public TimerClass(){
            setLayout(new BorderLayout());
            JPanel panel1 = new JPanel(new GridLayout(1, 3, 7, 7));
            JPanel panel2 = new JPanel(new GridLayout(1, 3, 4, 4));
            timerLabel = new JLabel("00 : 00 : 00");
            originalColor = timerLabel.getForeground();
            secTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (started) {
                        if (seconds == 0 && minutes == 0 && hours == 0) {
                            timerLabel.setForeground(originalColor);
                            JOptionPane.showMessageDialog(TimerClass.this, "Time Up", "Time's Up!", JOptionPane.INFORMATION_MESSAGE);
                            secTimer.stop();
                            addButton.setEnabled(true);
                        }
                        //Change the color when 5 seconds remain
                        if ((seconds <= 5 && seconds > 0) && minutes == 0 && hours == 0) {
                            timerLabel.setForeground(Color.RED);
                        }
                        if (seconds > 0) {

                            seconds--;
                            timerLabel.setText(String.format("%02d : %02d : %02d", hours, minutes, seconds));
                        } else {
                            if (minutes != 0 && hours == 0) {
                                minutes--;
                                seconds = 60;
                                seconds--;
                            } else if (minutes != 0 && hours != 0) {
                                minutes--;
                                seconds = 60;
                                seconds--;
                            } else if (minutes == 0 && hours != 0) {
                                minutes = 60;
                                minutes--;
                                seconds = 60;
                                seconds--;
                                hours--;
                            }
                            timerLabel.setText(String.format("%02d : %02d : %02d", hours, minutes, seconds));
                        }
                    }
                }
            });

            addButton = new JButton("Add");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {    // Action to run a new function which has been declared
                    new TimerSpinner();
                }
            });
            //creates a new timer that runs in separate thread.
            newButton = new JButton("New...");
            newButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame newFrame = new TimerClass();// TO DO CHECK HERE
                    newFrame.setVisible(true);
                }
            });
            panel1.add(timerLabel);
            panel1.add(addButton);
            panel1.add(newButton);
            startButton = new JButton("Start");
            stopButton = new JButton("Stop");
            resetButton = new JButton("Reset");
            startButton.setEnabled(false);
            stopButton.setEnabled(false);
            resetButton.setEnabled(false);
            panel2.add(startButton);
            panel2.add(stopButton);
            panel2.add(resetButton);
            startButton.addActionListener(new ButtonHandler());
            stopButton.addActionListener(new ButtonHandler());
            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    initHr = initMin = initSec = 00; //TO DO
                    timerLabel.setText(String.format("%02d : %02d : %02d", initHr, initMin, initSec));
                    hours = initHr;
                    minutes = initMin;
                    seconds = initSec;
                    secTimer.restart();

                }
            });

            add(panel1, BorderLayout.NORTH);
            add(panel2, BorderLayout.SOUTH);
        }


        class ButtonHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(startButton)) {
                    started = true;
                    secTimer.start();
                    addButton.setEnabled(false);

                } else if (e.getSource().equals(stopButton)) {
                    started = false;
                    secTimer.stop();
                    addButton.setEnabled(true);
                }
            }
        }

        class TimerSpinner extends JFrame {
            private GridBagLayout gbl;
            private GridBagConstraints gbc;

            public TimerSpinner() {
                super("Enter the Timer values");
                gbl = new GridBagLayout();
                gbc = new GridBagConstraints();

                Insets insets = new Insets(2, 5, 2, 5);
                setLayout(gbl);
                JLabel message = new JLabel("Start counting from..");

                JLabel hoursLabel = new JLabel("Hours");
                final JSpinner hourSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));

                JLabel minutesLabel = new JLabel("Minutes");
                final JSpinner minutesSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

                JLabel secondsLabel = new JLabel("Seconds");
                final JSpinner secondsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

                JButton okButton = new JButton("OK");
                JButton cancelButton = new JButton("Cancel");

                //adding the components;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.insets = insets;
                addComponents(message, 0, 0, 4, 1);

                addComponents(hoursLabel, 1, 0, 2, 1);
                addComponents(minutesLabel, 1, 2, 1, 1);
                addComponents(secondsLabel, 1, 3, 1, 1);

                gbc.fill = GridBagConstraints.HORIZONTAL;

                gbc.weightx = 2;
                addComponents(hourSpinner, 2, 0, 2, 1);
                gbc.weightx = 1;
                addComponents(minutesSpinner, 2, 2, 1, 1);
                addComponents(secondsSpinner, 2, 3, 1, 1);

                gbc.weightx = 0;
                addComponents(okButton, 4, 4, 2, 2);
                addComponents(cancelButton, 4, 6, 2, 2);

                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setSize(350, 150);
                setResizable(false);
                setLocationRelativeTo(TimerClass.this);
                addButton.setEnabled(false);
                setVisible(true);


                //adding action listeners to the buttons
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        hours = (Integer) hourSpinner.getValue();
                        initHr = hours;
                        minutes = (Integer) minutesSpinner.getValue();
                        initMin = minutes;
                        seconds = (Integer) secondsSpinner.getValue();
                        initSec = seconds;
                        timerLabel.setText(String.format("%02d : %02d : %02d", hours, minutes, seconds));
                        timerLabel.setForeground(originalColor);
                        startButton.setEnabled(true);
                        stopButton.setEnabled(true);
                        resetButton.setEnabled(true);
                        addButton.setEnabled(true);
                        TimerSpinner.this.dispose();
                    }
                });
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TimerSpinner.this.dispose();

                    }
                });
            }

            public void addComponents(JComponent component, int row, int column, int width, int height) {
                gbc.gridx = column;
                gbc.gridy = row;
                gbc.gridwidth = width;
                gbc.gridheight = height;
                gbl.setConstraints(component, gbc);
                add(component);
            }
        }
    }
    /*Coding for ANALOG CLOCK*/
    class Clock extends JFrame {

        ImageIcon img;

        private GregorianCalendar cal;
        private int[] x = new int[2];
        private int[] y = new int[2];
        private java.util.Timer clocktimer = new java.util.Timer();

        private TimeZone clockTimeZone = TimeZone.getDefault();

        public Clock() {
            this.setPreferredSize(new Dimension(210, 210));
            this.setMinimumSize(new Dimension(210, 210));
            clocktimer.schedule(new TickTimerTask(), 0, 1000);
        }

//        public static void main(String[] args) { // TO DO CHECK HERE
//            JFrame frame = new JFrame();
//            frame.setSize(300, 300);
//            frame.setTitle("Analog Clock");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            Clock m_AnalogClock = new Clock();
//            frame.add(m_AnalogClock);
//            frame.setVisible(true);
//
//        }

        public void paint(Graphics g) {

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            drawCardinals((Graphics2D) g);
            drawHands((Graphics2D) g);

        }

        void clockMinutes(int startRadius, int endRadius, double theta) {
            theta -= Math.PI / 2;
            x[0] = (int) (getWidth() / 2 + startRadius * Math.cos(theta));
            y[0] = (int) (getHeight() / 2 + startRadius * Math.sin(theta));
            x[1] = (int) (getWidth() / 2 + endRadius * Math.cos(theta));
            y[1] = (int) (getHeight() / 2 + endRadius * Math.sin(theta));
        }

        void drawCardinals(Graphics2D g) {
            g.setStroke(new BasicStroke(12));
            g.setColor(Color.black);

            for (double theta = 0; theta < Math.PI * 2; theta += Math.PI / 6) {
                clockMinutes(100, 100, theta);
                g.drawPolyline(x, y, 2);
            }
        }

        public void drawHands(Graphics2D g) {
            double h = 2 * Math.PI * (cal.get(Calendar.HOUR));
            double m = 2 * Math.PI * (cal.get(Calendar.MINUTE));
            double s = 2 * Math.PI * (cal.get(Calendar.SECOND));

            g.setStroke(new BasicStroke(3));	// Size of each clockwise

            clockMinutes(0, 55, h / 12 + m / (60 * 12));
            g.setColor(Color.BLACK);
            g.drawPolyline(x, y, 2);

            clockMinutes(0, 70, m / 60 + s / (60 * 60));
            g.setColor(Color.black);
            g.drawPolyline(x, y, 2);

            clockMinutes(0, 70, s / 60);
            g.setColor(Color.red);
            g.drawPolyline(x, y, 2);

            g.fillOval(getWidth() / 2 - 8, getHeight() / 2 - 8, 16, 16);
        }

        class TickTimerTask extends TimerTask {

            public void run() {
                cal = (GregorianCalendar) GregorianCalendar.getInstance(clockTimeZone);
                repaint();
            }
        }
    }
    public static void main(String arg[])
    {
        /*Create GUI interface*/
        MainWindow mainWindow = new MainWindow("Java Multimedia");
        mainWindow.setLocationRelativeTo(null);
    }
}


