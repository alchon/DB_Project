import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.*;

/**
 * @author Seo Hyeon-Beom
 */

public class Hotel extends JFrame implements ActionListener{

    JPanel panel;

    private Hotel() {
        setTitle("호텔 관리 시스템");
        setSize(900,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        AddPanel();
        AddTitle();
        AddBook();
        AddStatus();
        AddSearch();
        fuckyou();
        add(panel);
        setVisible(true);
    }

    private void AddPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 900, 800);
    }

    private void AddTitle() {
        JLabel label = new JLabel("호텔 관리 시스템",JLabel.CENTER);
        label.setBorder(new LineBorder(Color.BLACK,2));
        label.setFont(new Font("Sans Serif", Font.BOLD, 30));
        label.setBounds(200,30,500,50);
        panel.add(label);
    }

    private void AddBook() {
        JLabel booklabel = new JLabel("투숙예약",JLabel.CENTER);
        booklabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        booklabel.setBounds(20,120,150,40);

        JLabel namelabel = new JLabel("고객명",JLabel.LEFT);
        JTextField nametext = new JTextField(6);
        namelabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        namelabel.setBounds(65,180,150,30);
        nametext.setBounds(250,180,150,30);

        JLabel datelabel = new JLabel("체크인(YYYYMMDD)",JLabel.LEFT);
        JTextField datetext = new JTextField(6);
        datelabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        datelabel.setBounds(65,220,150,30);
        datetext.setBounds(250,220,150,30);

        JLabel daylabel = new JLabel("박",JLabel.LEFT);
        daylabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        daylabel.setBounds(65,260,150,30);

        String[] day = new String[31];
        for (int i=1; i<=31; i++) {
            day[i-1] = String.valueOf(i);
        }

        JComboBox daybox = new JComboBox(day);
        daybox.setBounds(250,260,150,30);

        JLabel roomlabel = new JLabel("객실",JLabel.LEFT);
        roomlabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        roomlabel.setBounds(65,300,150,30);

        String[] room = new String[20];
        int index = 0;
        for (int i=1; i<=2; i++) {
            for (int j=1; j<= 10; j++) {
                int tmp = (i*100) + j;
                room[index] = String.valueOf(tmp);
                index ++;
            }
        }

        JComboBox roombox = new JComboBox(room);
        roombox.setBounds(250,300,150,30);

        JButton registerbtn = new JButton("예약 등록/변경");
        JButton cancelbtn = new JButton("예약 취소");

        registerbtn.setBounds(90,345,120,30);
        cancelbtn.setBounds(260,345,120,30);

        panel.add(booklabel);
        panel.add(namelabel);
        panel.add(datelabel);
        panel.add(datetext);
        panel.add(nametext);
        panel.add(daylabel);
        panel.add(daybox);
        panel.add(roomlabel);
        panel.add(roombox);
        panel.add(registerbtn);
        panel.add(cancelbtn);
    }

    private void AddStatus() {
        JLabel statuslabel = new JLabel("객실 예약 현황",JLabel.CENTER);
        statuslabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        statuslabel.setBounds(450,120,150,40);

        SimpleDateFormat date = new SimpleDateFormat("(yyyy-MM-dd)",Locale.KOREA);
        Date currentTime = new Date();
        String Time = date.format(currentTime);
        JLabel datelabel = new JLabel(Time, JLabel.CENTER);
        datelabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        datelabel.setBounds(650,120,150,40);

        JTextField[] room = new JTextField[20];
        int index = 0;
        for (int i=1; i<=2; i++) {
            for (int j=1; j<= 10; j++) {
                int tmp = (i*100) + j;
                room[index] = new JTextField(String.valueOf(tmp));
                index ++;
            }
        }

        index = 0;
        for (int i=170; i<= 320; i+=50) {
            for (int j=470; j<= 730; j+= 65) {
                room[index].setBounds(j,i,60,45);
                index ++;
            }
        }

        panel.add(statuslabel);
        panel.add(datelabel);
        for (int i=0; i<20; i++) {
            room[i].setEditable(false);
            room[i].setHorizontalAlignment(JTextField.CENTER);
            room[i].setFont(new Font("Sans Serif", Font.BOLD, 20));
            room[i].setBorder(new LineBorder(Color.BLACK));
            panel.add(room[i]);
        }
    }

    private void AddSearch() {
        JLabel searchlabel = new JLabel("등록/조회",JLabel.CENTER);
        searchlabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        searchlabel.setBounds(20,450,150,40);
        panel.add(searchlabel);
        JTabbedPane tpane = new JTabbedPane();
        JPanel customer_panel = new JPanel();
        JPanel room_panel = new JPanel();
        JPanel client_panel = new JPanel();
        tpane.add("고객",customer_panel);
        tpane.add("객실", room_panel);
        tpane.add("직원", client_panel);
        tpane.setBounds(100,500,700,250);
//        panel.add(tpane);
    }

    private void fuckyou() {
        JLabel namelabel = new JLabel("고객명", JLabel.LEFT);
        namelabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
        namelabel.setBounds(100,550,50,40);
        JTextField nametext = new JTextField(6);
        nametext.setBounds(180,550,150,40);
        JButton registerbtn = new JButton("회원가입");
        registerbtn.setBounds(120,620,100,30);
        JButton searchbtn = new JButton("조회");
        searchbtn.setBounds(230,620,100,30);
        JTextArea infoarea = new JTextArea();
        infoarea.setEditable(false);
        infoarea.setBounds(380,530,400,150);
        panel.add(namelabel);
        panel.add(nametext);
        panel.add(registerbtn);
        panel.add(searchbtn);
        panel.add(infoarea);
    }

    public static void main(String[] args) {
        new Hotel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
