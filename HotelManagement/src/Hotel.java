import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * @author 서현범 (2016003590)
 */

/**
 * 날짜가 2017/12/60 이렇게 들어오면 터진다.
 *
 */

public class Hotel extends JFrame implements ActionListener{

    private static Connection dbTest;
    private String username;
    private String password;
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    // 로그인
    private JLabel idLabel = new JLabel("아이디");
    private JLabel pwdLabel = new JLabel("비밀번호");
    private JTextField idInput = new JTextField();
    private JPasswordField pwdInput = new JPasswordField();
    private JButton loginButton = new JButton("로그인");

    // 메뉴바
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("File");
    private JMenuItem menuItem = new JMenuItem("Open");
    // 제목
    private JLabel mainlabel = new JLabel("호텔 관리 시스템",JLabel.CENTER);
    // 투숙예약
    private JLabel booklabel = new JLabel("투숙예약",JLabel.CENTER);
    private JLabel namelabel = new JLabel("고객명",JLabel.LEFT);
    private JTextField nametext = new JTextField(6);
    private JLabel datelabel = new JLabel("체크인(YYYY/MM/DD)",JLabel.LEFT);
    private JTextField datetext = new JTextField(6);
    private JLabel daylabel = new JLabel("박",JLabel.LEFT);
    private JComboBox<String> daybox;
    private JLabel roomlabel = new JLabel("객실",JLabel.LEFT);
    private JComboBox<String> roombox;
    private JButton registerbtn = new JButton("예약 등록/변경");
    private JButton cancelbtn = new JButton("예약 취소");
    // 객실 예약 현황
    private JLabel statuslabel = new JLabel("객실 예약 현황",JLabel.CENTER);
    private JLabel datelabel2;
    private JTextField[] room = new JTextField[20];
    // 등록/조회
    private JLabel searchlabel = new JLabel("등록/조회",JLabel.CENTER);
    private JTabbedPane tpane = new JTabbedPane();
    // 고객
    private JPanel customer_panel = new JPanel();
    private JLabel customer_namelabel = new JLabel("고객명", JLabel.LEFT);
    private JTextField customer_nametext = new JTextField(6);
    private JButton customer_registerbtn = new JButton("회원가입");
    private JButton customer_searchbtn = new JButton("조회");
    private JTextArea customer_infoarea = new JTextArea();
    // 객실
    private JPanel room_panel = new JPanel();
    private JLabel roomlabel2 = new JLabel("객실", JLabel.LEFT);
    private JComboBox<String> roombox2;
    private JTextArea room_infoarea = new JTextArea();
    // 직원
    private JPanel client_panel = new JPanel();
    private JLabel client_namelabel = new JLabel("직원명", JLabel.LEFT);
    private JTextField client_nametext = new JTextField(6);
    private JButton client_registerbtn = new JButton("직원등록");
    private JButton client_searchbtn = new JButton("조회");
    private JTextArea client_infoarea = new JTextArea();
    // 파일 열기
    private File selectedFile;
    // 등록/조회 고객 회원가입 버튼
    private JFrame customer_fs = new JFrame("회원가입");
    private JLabel new_customer_name = new JLabel("고객명", JLabel.LEFT);
    private JTextField new_customer_nametext = new JTextField(6);
    private JLabel new_customer_sex = new JLabel("성별", JLabel.LEFT);
    private JComboBox<String> new_customer_sexbox;
    private JLabel new_customer_address = new JLabel("주소",JLabel.LEFT);
    private JComboBox<String> new_customer_addressbox;
    private JLabel new_customer_phone = new JLabel("연락처",JLabel.LEFT);
    private JTextField new_customer_phonetext = new JTextField(6);
    private JButton new_customer_registerbtn = new JButton("가입신청");
    private JButton new_customer_cancelbtn = new JButton("취소");
    // 등록/조회 직원 회원가입 버튼
    private JFrame client_fs = new JFrame("회원가입");
    private JLabel new_client_name = new JLabel("직원명",JLabel.LEFT);
    private JTextField new_client_nametext = new JTextField(6);
    private JLabel new_client_sex = new JLabel("성별", JLabel.LEFT);
    private JComboBox<String> new_client_sexbox;
    private JLabel new_client_address = new JLabel("주소",JLabel.LEFT);
    private JComboBox<String> new_client_addressbox;
    private JLabel new_client_phone = new JLabel("연락처", JLabel.LEFT);
    private JTextField new_client_phonetext = new JTextField(6);
    private JButton new_client_registerbtn = new JButton("직원등록");
    private JButton new_client_cancelbtn = new JButton("취소");
    // 등록되지 않은 사용자 팝업
    private JFrame not_customer_fs = new JFrame("안내");
    private JLabel not_customerLabel = new JLabel("가입되지 않은 고객입니다.");
    private JButton not_customerbtn = new JButton("확인");
    // 날짜가 중복
    private JFrame override_date_fs = new JFrame("안내");
    private JLabel override_dateLabel = new JLabel("이미 존재합니다.");
    private JButton override_datebtn = new JButton("확인");
    // 예약이 없
    private JFrame notreserve_fs = new JFrame("안내");
    private JLabel notreserveLabel = new JLabel("예약이 존재하지 않습니다.");
    private JButton notreservebtn = new JButton("확인");
    // 예약 등록/변경
    private JFrame new_reserve_fs = new JFrame("안내");
    private JLabel new_reserveLabel = new JLabel("예약 등록/변경이 되었습니다.");
    private JButton new_reservebtn = new JButton("확인");
    // 예약 취소
    private JFrame reserve_cancel_fs = new JFrame("안내");
    private JLabel reserve_cancelLabel = new JLabel("예약 취소 되었습니다.");
    private JButton reserve_cancelbtn = new JButton("확인");
    // 가입 성공
    private JFrame registerok_fs = new JFrame("안내");
    private JLabel registerokLabel = new JLabel("등록 되었습니다.");
    private JButton registerokbtn = new JButton("확인");
    // 날짜 형식 틀릴시
    private JFrame dateformat_fs = new JFrame("안내");
    private JLabel dateformatLabel = new JLabel("공백 또는 날짜 형식을 확인하세요.");
    private JButton dateformatbtn = new JButton("확인");

    private Hotel() {
        panel.setLayout(null);
        idLabel.setBounds(20, 10, 60, 30);
        pwdLabel.setBounds(20, 50, 60, 30);
        idInput.setBounds(100, 10, 80, 30);
        pwdInput.setBounds(100, 50, 80, 30);
        loginButton.setBounds(200, 25, 80, 35);
        loginButton.addActionListener(this);

        panel.add(idLabel);
        panel.add(pwdLabel);
        panel.add(idInput);
        panel.add(pwdInput);
        panel.add(loginButton);

        frame.add(panel);
        frame.setTitle("로그인");
        frame.setSize(320,130);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void ViewHotel() {
        frame.setVisible(false);
        frame = new JFrame();
        panel = new JPanel();
        panel.setLayout(null);
        setTitle("호텔 관리 시스템");
        setSize(900,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        setLayout(null);
        setResizable(false);
        AddPanel();
        Addmenu();
        AddTitle();
        AddBook();
        AddStatus();
        AddSearch();
        add(panel);
        createTable();
        reserveStatus();
        setVisible(true);
    }

    private void AddPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 900, 800);
    }

    private void Addmenu() {
        menuBar.add(menu);
        menu.add(menuItem);
        menuBar.setBounds(0,0,900,30);
        menuItem.addActionListener(this);
        panel.add(menuBar);
    }

    private void AddTitle() {
        mainlabel.setBorder(new LineBorder(Color.BLACK,2));
        mainlabel.setFont(new Font("Sans Serif", Font.BOLD, 30));
        mainlabel.setBounds(200,40,500,50);
        panel.add(mainlabel);
    }

    private void AddBook() {
        booklabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        booklabel.setBounds(20,120,150,40);

        namelabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        namelabel.setBounds(65,180,150,30);
        nametext.setBounds(250,180,150,30);

        datelabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        datelabel.setBounds(65,220,150,30);
        datetext.setBounds(250,220,150,30);

        daylabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        daylabel.setBounds(65,260,150,30);

        String[] day = new String[31];
        for (int i=1; i<=31; i++) {
            day[i-1] = String.valueOf(i);
        }
        daybox = new JComboBox<String>(day);

        daybox.setBounds(250,260,150,30);

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

        roombox = new JComboBox<String>(room);
        roombox.setBounds(250,300,150,30);

        registerbtn.setBounds(90,345,120,30);
        cancelbtn.setBounds(260,345,120,30);

        registerbtn.addActionListener(this);
        cancelbtn.addActionListener(this);

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
        statuslabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        statuslabel.setBounds(450,120,150,40);

        SimpleDateFormat date = new SimpleDateFormat("(yyyy-MM-dd)",Locale.KOREA);
        Date currentTime = new Date();
        String Time = date.format(currentTime);
        datelabel2 = new JLabel(Time, JLabel.CENTER);
        datelabel2.setFont(new Font("Sans Serif", Font.BOLD, 20));
        datelabel2.setBounds(650,120,150,40);

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
        panel.add(datelabel2);
        for (int i=0; i<20; i++) {
            room[i].setEditable(false);
            room[i].setHorizontalAlignment(JTextField.CENTER);
            room[i].setFont(new Font("Sans Serif", Font.BOLD, 20));
            room[i].setBorder(new LineBorder(Color.BLACK));
            panel.add(room[i]);
        }
    }

    private void AddSearch() {

        searchlabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        searchlabel.setBounds(20,450,150,40);
        panel.add(searchlabel);

        customer_panel.setLayout(null);
        customer_namelabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
        customer_namelabel.setBounds(40,40,50,40);
        customer_nametext.setBounds(120,40,150,40);
        customer_registerbtn.setBounds(60,110,100,30);
        customer_searchbtn.setBounds(170,110,100,30);
        customer_infoarea.setEditable(false);
        customer_infoarea.setBounds(320,20,400,150);
        customer_panel.add(customer_namelabel);
        customer_panel.add(customer_nametext);
        customer_panel.add(customer_registerbtn);
        customer_panel.add(customer_searchbtn);
        customer_panel.add(customer_infoarea);

        room_panel.setLayout(null);
        roomlabel2.setFont(new Font("Sans Serif", Font.BOLD, 15));
        roomlabel2.setBounds(40,40,50,40);
        String[] room = new String[20];
        int index = 0;
        for (int i=1; i<=2; i++) {
            for (int j=1; j<= 10; j++) {
                int tmp = (i*100) + j;
                room[index] = String.valueOf(tmp);
                index ++;
            }
        }
        roombox2 = new JComboBox<String>(room);
        roombox2.setBounds(120,40,150,40);
        room_infoarea.setEditable(false);
        room_infoarea.setBounds(320,20,400,150);
        room_panel.add(roomlabel2);
        room_panel.add(roombox2);
        room_panel.add(room_infoarea);

        client_panel.setLayout(null);
        client_namelabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
        client_namelabel.setBounds(40,40,50,40);
        client_nametext.setBounds(120,40,150,40);
        client_registerbtn.setBounds(60,110,100,30);
        client_searchbtn.setBounds(170,110,100,30);
        client_infoarea.setEditable(false);
        client_infoarea.setBounds(320,20,400,150);
        client_panel.add(client_namelabel);
        client_panel.add(client_nametext);
        client_panel.add(client_registerbtn);
        client_panel.add(client_searchbtn);
        client_panel.add(client_infoarea);

        customer_registerbtn.addActionListener(this);
        customer_searchbtn.addActionListener(this);
        client_registerbtn.addActionListener(this);
        roombox2.addActionListener(this);
        client_searchbtn.addActionListener(this);

        tpane.add("고객",customer_panel);
        tpane.add("객실", room_panel);
        tpane.add("직원", client_panel);
        tpane.setBounds(60,500,780,250);
        panel.add(tpane);
    }

    private void new_customer() {
        customer_fs.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                customer_fs.setVisible(false);
                customer_fs.dispose();
            }
        });
        customer_fs.setLayout(null);
        Container c = customer_fs.getContentPane();
        new_customer_name.setBounds(60, 60, 150, 30);
        new_customer_name.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        new_customer_nametext.setBounds(140, 60, 150, 30);
        new_customer_sex.setBounds(60, 120, 150, 30);
        new_customer_sex.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        String sex[] = new String[2];
        sex[0] = "남";
        sex[1] = "여";
        new_customer_sexbox = new JComboBox<String>(sex);
        new_customer_sexbox.setBounds(140, 120, 150, 30);
        new_customer_address.setBounds(60, 180, 150, 30);
        new_customer_address.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        String address[] = new String [17];
        address[0] = "서울";
        address[1] = "경기";
        address[2] = "인천";
        address[3] = "부산";
        address[4] = "대구";
        address[5] = "대전";
        address[6] = "경남";
        address[7] = "전남";
        address[8] = "충남";
        address[9] = "광주";
        address[10] = "울산";
        address[11] = "경북";
        address[12] = "전북";
        address[13] = "충북";
        address[14] = "강원";
        address[15] = "제주";
        address[16] = "세종";
        new_customer_addressbox = new JComboBox<String>(address);
        new_customer_addressbox.setBounds(140, 180, 150,30);
        new_customer_phone.setBounds(60, 240, 150, 30);
        new_customer_phone.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        new_customer_phonetext.setBounds(140, 240, 150, 30);
        new_customer_registerbtn.setBounds(50, 300, 120, 30);
        new_customer_cancelbtn.setBounds(200, 300, 120, 30);
        c.add(new_customer_name);
        c.add(new_customer_nametext);
        c.add(new_customer_sex);
        c.add(new_customer_sexbox);
        c.add(new_customer_address);
        c.add(new_customer_addressbox);
        c.add(new_customer_phone);
        c.add(new_customer_phonetext);
        c.add(new_customer_registerbtn);
        c.add(new_customer_cancelbtn);

        new_customer_registerbtn.addActionListener(this);
        new_customer_cancelbtn.addActionListener(this);

        customer_fs.setSize(400,400);
        customer_fs.setLocation(300,200);
        customer_fs.setVisible(true);
    }

    private void new_client() {
        client_fs.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client_fs.setVisible(false);
                client_fs.dispose();
            }
        });
        client_fs.setLayout(null);
        Container c = client_fs.getContentPane();
        new_client_name.setBounds(60, 60, 150, 30);
        new_client_name.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        new_client_nametext.setBounds(140, 60, 150, 30);
        new_client_sex.setBounds(60, 120, 150, 30);
        new_client_sex.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        String sex[] = new String[2];
        sex[0] = "남";
        sex[1] = "여";
        new_client_sexbox = new JComboBox<String>(sex);
        new_client_sexbox.setBounds(140, 120, 150, 30);
        new_client_address.setBounds(60, 180, 150, 30);
        new_client_address.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        String address[] = new String [17];
        address[0] = "서울";
        address[1] = "경기";
        address[2] = "인천";
        address[3] = "부산";
        address[4] = "대구";
        address[5] = "대전";
        address[6] = "경남";
        address[7] = "전남";
        address[8] = "충남";
        address[9] = "광주";
        address[10] = "울산";
        address[11] = "경북";
        address[12] = "전북";
        address[13] = "충북";
        address[14] = "강원";
        address[15] = "제주";
        address[16] = "세종";
        new_client_addressbox = new JComboBox<String>(address);
        new_client_addressbox.setBounds(140, 180, 150,30);
        new_client_phone.setBounds(60, 240, 150, 30);
        new_client_phone.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        new_client_phonetext.setBounds(140, 240, 150, 30);
        new_client_registerbtn.setBounds(50, 300, 120, 30);
        new_client_cancelbtn.setBounds(200, 300, 120, 30);
        c.add(new_client_name);
        c.add(new_client_nametext);
        c.add(new_client_sex);
        c.add(new_client_sexbox);
        c.add(new_client_address);
        c.add(new_client_addressbox);
        c.add(new_client_phone);
        c.add(new_client_phonetext);
        c.add(new_client_registerbtn);
        c.add(new_client_cancelbtn);

        new_client_cancelbtn.addActionListener(this);
        new_client_registerbtn.addActionListener(this);

        client_fs.setSize(400,400);
        client_fs.setLocation(300,200);
        client_fs.setVisible(true);
    }

    private void not_customer() {
        not_customer_fs.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                not_customer_fs.setVisible(false);
                not_customer_fs.dispose();
            }
        });
        not_customer_fs.setLayout(null);
        Container c = not_customer_fs.getContentPane();
        not_customerLabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        not_customerLabel.setBounds(70,20,200,20);
        not_customerbtn.setBounds(100,70,100,40);
        c.add(not_customerLabel);
        c.add(not_customerbtn);
        not_customerbtn.addActionListener(this);
        not_customer_fs.setSize(300,150);
        not_customer_fs.setLocation(300,200);
        not_customer_fs.setVisible(true);
    }

    private void override_date() {
        override_date_fs.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                override_date_fs.setVisible(false);
                override_date_fs.dispose();
            }
        });
        override_date_fs.setLayout(null);
        Container c = override_date_fs.getContentPane();
        override_dateLabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        override_dateLabel.setBounds(70,20,200,20);
        override_datebtn.setBounds(100,70,100,40);
        c.add(override_dateLabel);
        c.add(override_datebtn);
        override_datebtn.addActionListener(this);
        override_date_fs.setSize(300,150);
        override_date_fs.setLocation(300,200);
        override_date_fs.setVisible(true);
    }

    private void not_reserve() {
        notreserve_fs.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                notreserve_fs.setVisible(false);
                notreserve_fs.dispose();
            }
        });
        notreserve_fs.setLayout(null);
        Container c = notreserve_fs.getContentPane();
        notreserveLabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        notreserveLabel.setBounds(70,20,200,20);
        notreservebtn.setBounds(100,70,100,40);
        c.add(notreserveLabel);
        c.add(notreservebtn);
        notreservebtn.addActionListener(this);
        notreserve_fs.setSize(300,150);
        notreserve_fs.setLocation(300,200);
        notreserve_fs.setVisible(true);
    }

    private void new_reserve() {
        new_reserve_fs.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new_reserve_fs.setVisible(false);
                new_reserve_fs.dispose();
            }
        });
        new_reserve_fs.setLayout(null);
        Container c = new_reserve_fs.getContentPane();
        new_reserveLabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        new_reserveLabel.setBounds(70,20,200,20);
        new_reservebtn.setBounds(100,70,100,40);
        c.add(new_reserveLabel);
        c.add(new_reservebtn);
        new_reservebtn.addActionListener(this);
        new_reserve_fs.setSize(300,150);
        new_reserve_fs.setLocation(300,200);
        new_reserve_fs.setVisible(true);
    }

    private void reserve_cancel() {
        reserve_cancel_fs.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                reserve_cancel_fs.setVisible(false);
                reserve_cancel_fs.dispose();
            }
        });
        reserve_cancel_fs.setLayout(null);
        Container c = reserve_cancel_fs.getContentPane();
        reserve_cancelLabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        reserve_cancelLabel.setBounds(70,20,200,20);
        reserve_cancelbtn.setBounds(100,70,100,40);
        c.add(reserve_cancelLabel);
        c.add(reserve_cancelbtn);
        reserve_cancelbtn.addActionListener(this);
        reserve_cancel_fs.setSize(300,150);
        reserve_cancel_fs.setLocation(300,200);
        reserve_cancel_fs.setVisible(true);
    }

    private void registerok() {
        registerok_fs.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                registerok_fs.setVisible(false);
                registerok_fs.dispose();
            }
        });
        registerok_fs.setLayout(null);
        Container c = registerok_fs.getContentPane();
        registerokLabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        registerokLabel.setBounds(70,20,200,20);
        registerokbtn.setBounds(100,70,100,40);
        c.add(registerokLabel);
        c.add(registerokbtn);
        registerokbtn.addActionListener(this);
        registerok_fs.setSize(300,150);
        registerok_fs.setLocation(300,200);
        registerok_fs.setVisible(true);
    }

    private void dateformat() {
        dateformat_fs.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dateformat_fs.setVisible(false);
                dateformat_fs.dispose();
            }
        });
        dateformat_fs.setLayout(null);
        Container c = dateformat_fs.getContentPane();
        dateformatLabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        dateformatLabel.setBounds(50,20,250,20);
        dateformatbtn.setBounds(100,70,100,40);
        c.add(dateformatLabel);
        c.add(dateformatbtn);
        dateformatbtn.addActionListener(this);
        dateformat_fs.setSize(300,150);
        dateformat_fs.setLocation(300,200);
        dateformat_fs.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuItem) {
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = chooser.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
            }
            insertdata();
        }
        if(e.getSource().equals(loginButton)) {
            username = idInput.getText();
            password = new String(pwdInput.getPassword());
            connectDB();
        }
        if(e.getSource().equals(customer_registerbtn)) {
            new_customer();
        }
        if(e.getSource().equals(client_registerbtn)) {
            new_client();
        }
        if(e.getSource().equals(new_customer_cancelbtn)){
            customer_fs.setVisible(false);
            customer_fs.dispose();
        }
        if(e.getSource().equals(new_client_cancelbtn)) {
            client_fs.setVisible(false);
            client_fs.dispose();
        }
        if(e.getSource().equals(not_customerbtn)) {
            not_customer_fs.setVisible(false);
            not_customer_fs.dispose();
        }
        if(e.getSource().equals(notreservebtn)) {
            notreserve_fs.setVisible(false);
            notreserve_fs.dispose();
        }
        if(e.getSource().equals(override_datebtn)) {
            override_date_fs.setVisible(false);
            override_date_fs.dispose();
        }
        if(e.getSource().equals(reserve_cancelbtn)) {
            reserve_cancel_fs.setVisible(false);
            reserve_cancel_fs.dispose();
        }
        if(e.getSource().equals(new_reservebtn)) {
            new_reserve_fs.setVisible(false);
            new_reserve_fs.dispose();
        }
        if(e.getSource().equals(registerokbtn)) {
            registerok_fs.setVisible(false);
            registerok_fs.dispose();
        }
        if(e.getSource().equals(dateformatbtn)) {
            dateformat_fs.setVisible(false);
            dateformat_fs.dispose();
        }
        if(e.getSource().equals(registerbtn)) {
            reservation();
            reserveStatus();
        }
        if(e.getSource().equals(cancelbtn)) {
            reservationcancel();
            reserveStatus();
        }
        if(e.getSource().equals(customer_searchbtn)){
            customer_search();
        }
        if(e.getSource().equals(new_customer_registerbtn)) {
            customer_register();
        }
        if(e.getSource().equals(roombox2)) {
            room_search();
        }
        if(e.getSource().equals(new_client_registerbtn)) {
            client_register();
        }
        if(e.getSource().equals(client_searchbtn)) {
            client_search();
        }
    }

    private void connectDB() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            dbTest = DriverManager.getConnection("jdbc:oracle:thin:" + "@localhost:1521:XE", username, password);
            System.out.println("Connect Success -id: "+username);
            ViewHotel();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Fail to connect");
        } catch(Exception e) {
            System.out.println("Exception:"+e);
        }
    }

    private void createTable(){
        try {
            String sqlStr = "select count(*) from all_tables where table_name = 'CUSTOMER'";
            PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if(rs.getString("count(*)").equals("0")) {
                sqlStr = "create table CUSTOMER (" +
                        " NAME varchar2(20)," +
                        " sex   varchar2(20)," +
                        " address   varchar2(20)," +
                        " phone varchar2(20)," +
                        " primary key(NAME))";
                stmt = dbTest.prepareStatement(sqlStr);
                rs = stmt.executeQuery();
            }
            sqlStr = "select count(*) from all_tables where table_name = 'CLIENT'";
            stmt = dbTest.prepareStatement(sqlStr);
            rs = stmt.executeQuery();
            rs.next();
            if(rs.getString("count(*)").equals("0")) {
                sqlStr = "create table CLIENT (" +
                        " name  varchar2(20)," +
                        " sex   varchar2(20)," +
                        " address   varchar2(20)," +
                        " phone varchar2(20)," +
                        " primary key(name))";
                stmt = dbTest.prepareStatement(sqlStr);
                rs = stmt.executeQuery();
            }
            sqlStr = "select count(*) from all_tables where table_name = 'ROOM'";
            stmt = dbTest.prepareStatement(sqlStr);
            rs = stmt.executeQuery();
            rs.next();
            if(rs.getString("count(*)").equals("0")) {
                sqlStr = "create table ROOM(" +
                        " num   varchar2(20)," +
                        " capacity  varchar2(20)," +
                        " type  varchar2(20)," +
                        " primary key(num))";
                stmt = dbTest.prepareStatement(sqlStr);
                rs = stmt.executeQuery();
            }
            sqlStr = "select count(*) from all_tables where table_name = 'RESERVATION'";
            stmt = dbTest.prepareStatement(sqlStr);
            rs = stmt.executeQuery();
            rs.next();
            if(rs.getString("count(*)").equals("0")) {
                sqlStr = "create table RESERVATION(" +
                        " customer_name  varchar2(20)," +
                        " client_name   varchar2(20)," +
                        " days  INT ," +
                        " room  varchar2(20)," +
                        " checkin    date," +
                        " foreign key (customer_name) REFERENCES CUSTOMER (NAME)," +
                        " foreign key (client_name) REFERENCES CLIENT(NAME)," +
                        " foreign key (room) REFERENCES ROOM(NUM))";
                stmt = dbTest.prepareStatement(sqlStr);
                rs = stmt.executeQuery();
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertdata() {
        if (selectedFile == null) {
            return;
        }
        try {
            FileReader fr = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int step = 0;
            String sqlStr;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            while((line = br.readLine()) != null) {
                if(step == 0) {
                    int count = Integer.parseInt(line);
                    for(int i=0; i<count; i++) {
                        String[] str = br.readLine().split("\\s+");
                        sqlStr = "insert into CUSTOMER values('"+str[0]+"','"+str[1]+"','"+str[2]+"','"+str[3]+"')";
                        stmt = dbTest.prepareStatement(sqlStr);
                        rs = stmt.executeQuery();
                    }
                }
                if(step == 1) {
                    int count = Integer.parseInt(line);
//                    System.out.println("count: "+count);
                    for(int i=0; i<count; i++) {
                        String[] str = br.readLine().split("\\s+");
                        sqlStr = "insert into CLIENT values('"+str[0]+"','"+str[1]+"','"+str[2]+"','"+str[3]+"')";
                        stmt = dbTest.prepareStatement(sqlStr);
                        rs = stmt.executeQuery();
                    }
                }
                if(step == 2) {
                    int count = Integer.parseInt(line);
//                    System.out.println("count: "+count);
                    for(int i=0; i<count; i++) {
                        String[] str = br.readLine().split("\\s+");
                        sqlStr = "insert into ROOM values('"+str[0]+"','"+str[1]+"','"+str[2]+"')";
                        stmt = dbTest.prepareStatement(sqlStr);
                        rs = stmt.executeQuery();
                    }
                }
                step++;
            }
            rs.close();
            stmt.close();
            br.close();
            fr.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reservation() {
        String name = nametext.getText();
        String checkin = datetext.getText();
        String days = (String) daybox.getSelectedItem();
        String room = (String) roombox.getSelectedItem();
        SimpleDateFormat dateFormatParser = new SimpleDateFormat("YYYY/MM/DD");
        try {
            dateFormatParser.parse(checkin);
            try {
                String sqlStr = "select count(*) from CUSTOMER where name = '"+name+"'";
                PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
                ResultSet rs = stmt.executeQuery();
                String[] inputdate = checkin.split("/");
                String[] compareinputdate = new String[Integer.parseInt(days)];
                for(int i=0; i<Integer.parseInt(days); i++) {
                    if(inputdate[1]=="1" || inputdate[1]=="3" || inputdate[1]=="5" || inputdate[1]=="7" ||
                            inputdate[1]=="8" || inputdate[1]=="10" || inputdate[1]=="12") {
                        if(Integer.parseInt(inputdate[2]) + i > 31) {
                            if(inputdate[1]=="12") {
                                compareinputdate[i] = String.valueOf(Integer.parseInt(inputdate[0])+1)+"/1/"+String.valueOf(Integer.parseInt(inputdate[2])+i-31);
                            }
                            else {
                                compareinputdate[i] = inputdate[0]+"/"+String.valueOf(Integer.parseInt(inputdate[1])+1)+"/"+String.valueOf(Integer.parseInt(inputdate[2])+i-31);
                            }
                        }
                        else {
                            compareinputdate[i] = inputdate[0]+"/"+inputdate[1]+"/"+String.valueOf(Integer.parseInt(inputdate[2])+i);
                        }
                    }
                    else {
                        if(Integer.parseInt(inputdate[2]) + i > 30) {
                            if(inputdate[1]=="12") {
                                compareinputdate[i] = String.valueOf(Integer.parseInt(inputdate[0])+1)+"/1/"+String.valueOf(Integer.parseInt(inputdate[2])+i-30);
                            }
                            else {
                                compareinputdate[i] = inputdate[0]+"/"+String.valueOf(Integer.parseInt(inputdate[1])+1)+"/"+String.valueOf(Integer.parseInt(inputdate[2])+i-30);
                            }
                        }
                        else {
                            compareinputdate[i] = inputdate[0]+"/"+inputdate[1]+"/"+String.valueOf(Integer.parseInt(inputdate[2])+i);
                        }
                    }
                }
                rs.next();
                if(rs.getString("count(*)").equals("0")) {
                    not_customer();
                }
                else {
                    sqlStr = "select count(*) from RESERVATION where customer_name = '"+name+"' or room = '"+room+"'";
                    stmt = dbTest.prepareStatement(sqlStr);
                    rs = stmt.executeQuery();
                    rs.next();
                    if(rs.getString("count(*)").equals("0")) {
                        sqlStr = "select * from (select name from CLIENT order by DBMS_RANDOM.VALUE) where ROWNUM <= 1";
                        stmt = dbTest.prepareStatement(sqlStr);
                        rs = stmt.executeQuery();
                        rs.next();
                        String clientname = rs.getString("NAME");
                        sqlStr = "insert into RESERVATION values('"+name+"','"+clientname+"','"+days+"','"+room+"','"+checkin+"')";
                        stmt = dbTest.prepareStatement(sqlStr);
                        rs = stmt.executeQuery();
                        new_reserve();
                    }
                    else {
                        sqlStr = "select * from RESERVATION where customer_name = '"+name+"' or room = '"+room+"'";
                        stmt = dbTest.prepareStatement(sqlStr);
                        rs = stmt.executeQuery();
                        boolean tmp = false;
                        while(rs.next()) {
                            String day = rs.getString("days");
                            String date = rs.getString("checkin");
                            String[] querydate = date.split("-");
                            String[] datetmp = querydate[2].split(" ");
                            querydate[2] = datetmp[0];
                            String[] comparequerydate = new String[Integer.parseInt(day)];
                            for(int i=0; i<Integer.parseInt(day); i++) {
                                if(querydate[1]=="1" || querydate[1]=="3" || querydate[1]=="5" || querydate[1]=="7" ||
                                        querydate[1]=="8" || querydate[1]=="10" || querydate[1]=="12") {
                                    if(Integer.parseInt(querydate[2]) + i > 31) {
                                        if(querydate[1]=="12") {
                                            comparequerydate[i] = String.valueOf(Integer.parseInt(querydate[0])+1)+"/1/"+String.valueOf(Integer.parseInt(querydate[2])+i-31);
                                        }
                                        else {
                                            comparequerydate[i] = querydate[0]+"/"+String.valueOf(Integer.parseInt(querydate[1])+1)+"/"+String.valueOf(Integer.parseInt(querydate[2])+i-31);
                                        }
                                    }
                                    else {
                                        comparequerydate[i] = querydate[0]+"/"+querydate[1]+"/"+String.valueOf(Integer.parseInt(querydate[2])+i);
                                    }
                                }
                                else {
                                    if(Integer.parseInt(querydate[2]) + i > 30) {
                                        if(querydate[1]=="12") {
                                            comparequerydate[i] = String.valueOf(Integer.parseInt(querydate[0])+1)+"/1/"+String.valueOf(Integer.parseInt(querydate[2])+i-30);
                                        }
                                        else {
                                            comparequerydate[i] = querydate[0]+"/"+String.valueOf(Integer.parseInt(querydate[1])+1)+"/"+String.valueOf(Integer.parseInt(querydate[2])+i-30);
                                        }
                                    }
                                    else {
                                        comparequerydate[i] = querydate[0]+"/"+querydate[1]+"/"+String.valueOf(Integer.parseInt(querydate[2])+i);
                                    }
                                }
                            }
                            for(int i=0; i<Integer.parseInt(days); i++) {
                                for (int j=0; j<Integer.parseInt(day); j++) {
                                    if(compareinputdate[i].equals(comparequerydate[j])) {
                                        tmp = true;
                                        break;
                                    }
                                }
                                if(tmp == true) {
                                    break;
                                }
                            }
                        }
                        if(tmp == true) {
                            override_date();
                        }
                        else {
                            sqlStr = "select * from (select name from CLIENT order by DBMS_RANDOM.VALUE) where ROWNUM <= 1";
                            stmt = dbTest.prepareStatement(sqlStr);
                            rs = stmt.executeQuery();
                            rs.next();
                            String clientname = rs.getString("NAME");
                            sqlStr = "insert into RESERVATION values('"+name+"','"+clientname+"',"+days+",'"+room+"','"+checkin+"')";
                            stmt = dbTest.prepareStatement(sqlStr);
                            rs = stmt.executeQuery();
                            new_reserve();
                        }
                    }
                }
                stmt.close();
                rs.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        } catch(Exception e) {
            dateformat();
        }
    }

    private void reservationcancel() {
        String name = nametext.getText();
        String checkin = datetext.getText();
        String days = (String) daybox.getSelectedItem();
        String room = (String) roombox.getSelectedItem();
        try {
            String sqlStr = "select * from RESERVATION where customer_name = '"+name+"'and days='"+days+"'and room='"+room+"'and checkin='"+checkin+"'";
            PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                not_reserve();
            }
            else {
                sqlStr = "delete from RESERVATION where customer_name = '"+name+"'and days="+days+"and room='"+room+"'and checkin='"+checkin+"'";
                stmt = dbTest.prepareStatement(sqlStr);
                rs = stmt.executeQuery();
                reserve_cancel();
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void reserveStatus() {
        noreserve();
        SimpleDateFormat date = new SimpleDateFormat("(yyyy-MM-dd)",Locale.KOREA);
        Date currentTime = new Date();
        String Time = date.format(currentTime);
        Time = Time.substring(1, Time.length()-1);
//        String[] Timetmp = Time.split("-");
        try {
            String sqlStr = "select * from RESERVATION";
            PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String query_day = rs.getString("days");
                String query_date = rs.getString("checkin");
                String query_room = rs.getString("room");
                String[] querydate = query_date.split("-");
                String[] datetmp = querydate[2].split(" ");
                querydate[2] = datetmp[0];
                String[] comparequerydate = new String[Integer.parseInt(query_day)];
                for(int i=0; i<Integer.parseInt(query_day); i++) {
                    if(querydate[1]=="1" || querydate[1]=="3" || querydate[1]=="5" || querydate[1]=="7" ||
                            querydate[1]=="8" || querydate[1]=="10" || querydate[1]=="12") {
                        if(Integer.parseInt(querydate[2]) + i > 31) {
                            if(querydate[1]=="12") {
                                comparequerydate[i] = String.valueOf(Integer.parseInt(querydate[0])+1)+"-1-"+String.valueOf(Integer.parseInt(querydate[2])+i-31);
                            }
                            else {
                                comparequerydate[i] = querydate[0]+"-"+String.valueOf(Integer.parseInt(querydate[1])+1)+"-"+String.valueOf(Integer.parseInt(querydate[2])+i-31);
                            }
                        }
                        else {
                            comparequerydate[i] = querydate[0]+"-"+querydate[1]+"-"+String.valueOf(Integer.parseInt(querydate[2])+i);
                        }
                    }
                    else {
                        if(Integer.parseInt(querydate[2]) + i > 30) {
                            if(querydate[1]=="12") {
                                comparequerydate[i] = String.valueOf(Integer.parseInt(querydate[0])+1)+"-1-"+String.valueOf(Integer.parseInt(querydate[2])+i-30);
                            }
                            else {
                                comparequerydate[i] = querydate[0]+"-"+String.valueOf(Integer.parseInt(querydate[1])+1)+"-"+String.valueOf(Integer.parseInt(querydate[2])+i-30);
                            }
                        }
                        else {
                            comparequerydate[i] = querydate[0]+"-"+querydate[1]+"-"+String.valueOf(Integer.parseInt(querydate[2])+i);
                        }
                    }
                }
                for(int i=0; i<Integer.parseInt(query_day); i++) {
                    String[] formattmp = comparequerydate[i].split("-");
                    if(Integer.parseInt(formattmp[1]) < 10) {
                        formattmp[1] = "0"+formattmp[1];
                    }
                    if(Integer.parseInt(formattmp[2])<10) {
                        formattmp[2] = "0"+formattmp[2];
                    }
                    comparequerydate[i] = formattmp[0]+"-"+formattmp[1]+"-"+formattmp[2];
//                    System.out.println(comparequerydate[i]);
                    if(comparequerydate[i].equals(Time)) {
                        for(int j=0; j<20; j++) {
                            if (room[j].getText().equals(query_room)) {
                                room[j].setBackground(Color.yellow);
                                room[j].setForeground(Color.red);
                                break;
                            }
                        }
                    }
                }
            }
            stmt.close();
            rs.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void noreserve() {
        for(int i=0; i<20; i++) {
            room[i].setBackground(null);
            room[i].setForeground(null);
        }
    }

    private void customer_search() {
        customer_infoarea.selectAll();
        customer_infoarea.replaceSelection("");
        String name = customer_nametext.getText();
        try {
            String sqlStr = "select count(*) from CUSTOMER where name='"+name+"'";
            PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if(rs.getString("count(*)").equals("0")) {
                customer_infoarea.append("검색결과없음");
            }
            else {
                sqlStr = "select * from CUSTOMER where name='"+name+"'";
                stmt = dbTest.prepareStatement(sqlStr);
                rs = stmt.executeQuery();
                rs.next();
                String cus_name = rs.getString("name");
                String cus_sex = rs.getString("sex");
                String cus_address = rs.getString("address");
                String cus_phone = rs.getString("phone");
                customer_infoarea.append("고객명: "+cus_name+"\n");
                customer_infoarea.append("성별: "+cus_sex+"\n");
                customer_infoarea.append("주소: "+cus_address+"\n");
                customer_infoarea.append("연락쳐: "+cus_phone+"\n");

                sqlStr = "select count(*) from RESERVATION where customer_name='"+cus_name+"'";
                stmt = dbTest.prepareStatement(sqlStr);
                rs = stmt.executeQuery();
                rs.next();

                if(rs.getString("count(*)").equals("0")) {
                    customer_infoarea.append("총 투숙기간: 0 \n");
                    customer_infoarea.append("최근 투숙일: x \n");
                    customer_infoarea.append("객실전담직원(최다): x \t(0회)");
                }
                else {
                    sqlStr = "select sum(days) from CUSTOMER natural join RESERVATION where CUSTOMER.name = RESERVATION.customer_name and name='"+name+"'";
                    stmt = dbTest.prepareStatement(sqlStr);
                    rs = stmt.executeQuery();
                    rs.next();
                    String sumdays = rs.getString("sum(days)");
                    sqlStr = "select checkin from CUSTOMER natural join RESERVATION where CUSTOMER.name = RESERVATION.customer_name and name='"+name+"' order by checkin DESC";
                    stmt = dbTest.prepareStatement(sqlStr);
                    rs = stmt.executeQuery();
                    rs.next();
                    String recent = rs.getString("checkin");
                    String[] recenttmp = recent.split(" ");
                    recent = recenttmp[0];
                    sqlStr = "select client_name, count(client_name) from CUSTOMER natural join RESERVATION where CUSTOMER.name = RESERVATION.customer_name and name='"+name+"' group by client_name order by count(client_name) DESC";
                    stmt = dbTest.prepareStatement(sqlStr);
                    rs = stmt.executeQuery();
                    rs.next();
                    String client_name = rs.getString("client_name");
                    String client_count = rs.getString("count(client_name)");

                    customer_infoarea.append("총 투숙기간: "+sumdays+"\n");
                    customer_infoarea.append("최근 투숙일: "+recent+"\n");
                    customer_infoarea.append("객실전담직원(최다): "+client_name+"\t("+client_count+"회)");
                }
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void customer_register() {
        String name = new_customer_nametext.getText();
        String sex = (String) new_customer_sexbox.getSelectedItem();
        String address = (String) new_customer_addressbox.getSelectedItem();
        String phone = new_customer_phonetext.getText();
        if(name.isEmpty() || phone.isEmpty()) {
            dateformat();
        }
        else {
            try {
                String sqlStr = "select count(*) from CUSTOMER where name ='"+name+"'";
                PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                if(rs.getString("count(*)").equals("0")) {
                    sqlStr = "insert into CUSTOMER values('"+name+"','"+sex+"','"+address+"','"+phone+"')";
                    stmt = dbTest.prepareStatement(sqlStr);
                    rs = stmt.executeQuery();
                    registerok();
                }
                else {
                    override_date();
                }
                stmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void room_search() {
        room_infoarea.selectAll();
        room_infoarea.replaceSelection("");
        String room_box = (String) roombox2.getSelectedItem();
        try {
            String sqlStr = "select * from ROOM where num='"+room_box+"'";
            PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String room_num = rs.getString("num");
            String room_capacity = rs.getString("capacity");
            String room_type = rs.getString("type");
            String room_status;
            int index=0;
            int index2=0;
            for (int i=1; i<=2; i++) {
                for (int j=1; j<= 10; j++) {
                    int tmp = (i*100) + j;
                    if(tmp == Integer.parseInt(room_num)) {
                        index2 = index;
                    }
                    index++;
                }
            }
            if(room[index2].getBackground().equals(Color.yellow)) {
                room_status = "투숙중";
            }
            else {
                room_status = "빈방";
            }
            sqlStr = "select count(*) from RESERVATION where room='"+room_box+"'";
            stmt = dbTest.prepareStatement(sqlStr);
            rs = stmt.executeQuery();
            rs.next();
            room_infoarea.append("방번호: "+room_num+"\n");
            room_infoarea.append("수용인원: "+room_capacity+"\n");
            room_infoarea.append("타입: "+room_type+"\n");
            room_infoarea.append("상태: "+room_status+"\n");
            if(rs.getString("count(*)").equals("0")) {
                room_infoarea.append("투숙고객(최다): x   (0회)\n");
                room_infoarea.append("객실전담직원(최다): x     (0회)");
            }
            else {
                sqlStr = "select customer_name, count(customer_name) from CUSTOMER natural join RESERVATION where CUSTOMER.name = RESERVATION.customer_name and room='"+room_box+"' group by customer_name order by count(customer_name) DESC";
                stmt = dbTest.prepareStatement(sqlStr);
                rs = stmt.executeQuery();
                rs.next();
                String customer_name = rs.getString("customer_name");
                String customer_count = rs.getString("count(customer_name)");
                sqlStr = "select client_name, count(client_name) from CLIENT natural join RESERVATION where CLIENT.name = RESERVATION.client_name and room='"+room_box+"' group by client_name order by count(client_name) DESC";
                stmt = dbTest.prepareStatement(sqlStr);
                rs = stmt.executeQuery();
                rs.next();
                String client_name = rs.getString("client_name");
                String client_count = rs.getString("count(client_name)");
                room_infoarea.append("투숙고객(최다): "+customer_name+"\t("+customer_count+"회)\n");
                room_infoarea.append("객실전담직원(최다): "+client_name+"\t("+client_count+"회)");
            }
            stmt.close();
            rs.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void client_search() {
        client_infoarea.selectAll();
        client_infoarea.replaceSelection("");
        String name = client_nametext.getText();
        try {
            String sqlStr = "select count(*) from CLIENT where name='"+name+"'";
            PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if(rs.getString("count(*)").equals("0")) {
                client_infoarea.append("검색결과없음");
            }
            else {
                sqlStr = "select * from CLIENT where name='"+name+"'";
                stmt = dbTest.prepareStatement(sqlStr);
                rs = stmt.executeQuery();
                rs.next();
                String cli_name = rs.getString("name");
                String cli_sex = rs.getString("sex");
                String cli_address = rs.getString("address");
                String cli_phone = rs.getString("phone");
                client_infoarea.append("직원명: "+cli_name+"\n");
                client_infoarea.append("성별: "+cli_sex+"\n");
                client_infoarea.append("주소: "+cli_address+"\n");
                client_infoarea.append("연락처: "+cli_phone+"\n");

                sqlStr = "select count(*) from RESERVATION where client_name='"+cli_name+"'";
                stmt = dbTest.prepareStatement(sqlStr);
                rs = stmt.executeQuery();
                rs.next();
                if(rs.getString("count(*)").equals("0")) {
                    client_infoarea.append("접대 고객(최다): x    (0회)\n");
                    client_infoarea.append("관리 객실(최다): x    (0회)");
                }
                else {
                    sqlStr = "select customer_name, count(customer_name) from CLIENT natural join RESERVATION where CLIENT.name = RESERVATION.client_name and client_name='"+cli_name+"' group by customer_name order by count(customer_name) DESC";
                    stmt = dbTest.prepareStatement(sqlStr);
                    rs = stmt.executeQuery();
                    rs.next();
                    String cus_name = rs.getString("customer_name");
                    String cus_count = rs.getString("count(customer_name)");
                    sqlStr = "select room, count(room) from CLIENT natural join RESERVATION where CLIENT.name = RESERVATION.client_name and client_name='"+cli_name+"' group by room order by count(room) DESC";
                    stmt = dbTest.prepareStatement(sqlStr);
                    rs = stmt.executeQuery();
                    rs.next();
                    String room_num = rs.getString("room");
                    String room_count = rs.getString("count(room)");
                    client_infoarea.append("접대 고객(최다): "+cus_name+"\t("+cus_count+"회)\n");
                    client_infoarea.append("관리 객실(최다): "+room_num+"\t("+room_count+"회)");
                }
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void client_register() {
        String name = new_client_nametext.getText();
        String sex = (String) new_client_sexbox.getSelectedItem();
        String address = (String) new_client_addressbox.getSelectedItem();
        String phone = new_client_phonetext.getText();
        if(name.isEmpty() || phone.isEmpty()) {
            dateformat();
        }
        else {
            try {
                String sqlStr = "select count(*) from CLIENT where name='"+name+"'";
                PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                if(rs.getString("count(*)").equals("0")) {
                    sqlStr = "insert into CLIENT values('"+name+"','"+sex+"','"+address+"','"+phone+"')";
                    stmt = dbTest.prepareStatement(sqlStr);
                    rs = stmt.executeQuery();
                    registerok();
                }
                else {
                    override_date();
                }
                stmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Hotel();
    }
}
