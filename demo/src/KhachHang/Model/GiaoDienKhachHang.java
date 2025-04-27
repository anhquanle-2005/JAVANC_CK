import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GiaoDienKhachHang extends JFrame {
    private JPanel mainPanel;
    private JLabel lblMayDangSuDung;
    private JPanel drinkSelectionPanel;
    private JLabel lblSelectedDrink;
    private JLabel lblSelectedFood;
    private JSplitPane splitPane;
    private Map<String, Integer> cart;
    private JLabel lblTimeRemaining; // JLabel để hiển thị thời gian còn lại
    private long timeRemainingSeconds; // Thời gian còn lại (giây)
    private Timer timer; // Timer để đếm ngược
    private boolean warningShown; // Kiểm soát thông báo sắp hết thời gian

    public GiaoDienKhachHang() {
        cart = new HashMap<>();
        timeRemainingSeconds = 7200; // Ví dụ: 2 giờ (7200 giây)
        warningShown = false;
        setTitle("Quán Net - Giao Diện Khách Hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setMinimumSize(new Dimension(1024, 768));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sidebarWidth = (int) (screenSize.width * 0.15);

        setLayout(new BorderLayout());

        // Sidebar panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(200, 220, 255));
        sidebarPanel.setPreferredSize(new Dimension(sidebarWidth, screenSize.height));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        JLabel lblXinChao = new JLabel("Xin chào: Võ Hoàng Định", SwingConstants.CENTER);
        lblXinChao.setFont(new Font("Arial", Font.BOLD, 15));
        lblXinChao.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(Box.createVerticalStrut(20));
        sidebarPanel.add(lblXinChao);
        sidebarPanel.add(Box.createVerticalStrut(80));

        JLabel Trangchu = new JLabel("Trang chủ", SwingConstants.CENTER);
        Trangchu.setFont(new Font("Arial", Font.BOLD, 15));
        Trangchu.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(Trangchu);
        sidebarPanel.add(Box.createVerticalStrut(30));

        // Tree setup
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Menu");
        DefaultMutableTreeNode dichVu = new DefaultMutableTreeNode("Dịch vụ");
        DefaultMutableTreeNode chonMon = new DefaultMutableTreeNode("Chọn món");
        DefaultMutableTreeNode choiGame = new DefaultMutableTreeNode("Chơi game");
        DefaultMutableTreeNode hoaDon = new DefaultMutableTreeNode("Hóa đơn");
        DefaultMutableTreeNode dangXuat = new DefaultMutableTreeNode("Đăng xuất");

        dichVu.add(chonMon);
        root.add(dichVu);
        root.add(choiGame);
        root.add(hoaDon);
        root.add(dangXuat);

        JTree menuTree = new JTree(new DefaultTreeModel(root));
        menuTree.setBackground(new Color(255, 255, 255));
        menuTree.setFont(new Font("Arial", Font.BOLD, 14));
        menuTree.setRowHeight(30);
        menuTree.setRootVisible(true);
        menuTree.setShowsRootHandles(true);
        menuTree.setRowHeight(50);

        JScrollPane treeScrollPane = new JScrollPane(menuTree);
        treeScrollPane.setBorder(null);
        sidebarPanel.add(treeScrollPane);

        // Main panel
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel lblChaoMung = new JLabel("Chào mừng bạn đến với Quán Net!", SwingConstants.CENTER);
        lblChaoMung.setFont(new Font("Arial", Font.BOLD, 24));
        lblChaoMung.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(50));
        mainPanel.add(lblChaoMung);

        JLabel lblSoDu = new JLabel("Số dư: 50,000 VNĐ", SwingConstants.CENTER);
        lblSoDu.setFont(new Font("Arial", Font.PLAIN, 18));
        lblSoDu.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(lblSoDu);

        JLabel lblThoiGianChoi = new JLabel("Thời gian đã chơi: 2 giờ", SwingConstants.CENTER);
        lblThoiGianChoi.setFont(new Font("Arial", Font.PLAIN, 18));
        lblThoiGianChoi.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(lblThoiGianChoi);

        lblMayDangSuDung = new JLabel("Máy đang sử dụng: Máy 5", SwingConstants.CENTER);
        lblMayDangSuDung.setFont(new Font("Arial", Font.PLAIN, 18));
        lblMayDangSuDung.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(lblMayDangSuDung);

        lblSelectedDrink = new JLabel("Đồ uống đã chọn: Chưa chọn", SwingConstants.CENTER);
        lblSelectedDrink.setFont(new Font("Arial", Font.PLAIN, 18));
        lblSelectedDrink.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(lblSelectedDrink);

        lblSelectedFood = new JLabel("Thức ăn đã chọn: Chưa chọn", SwingConstants.CENTER);
        lblSelectedFood.setFont(new Font("Arial", Font.PLAIN, 18));
        lblSelectedFood.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(lblSelectedFood);

        // Dịch vụ panel
        drinkSelectionPanel = new JPanel();
        drinkSelectionPanel.setBackground(Color.WHITE);
        drinkSelectionPanel.setLayout(new BorderLayout());

        // Header section with title, filter, and buttons
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BorderLayout());

        JLabel lblDichVu = new JLabel("Dịch vụ", SwingConstants.LEFT);
        lblDichVu.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(lblDichVu, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JLabel lblLocTheo = new JLabel("Lọc theo phẩm:");
        lblLocTheo.setFont(new Font("Arial", Font.PLAIN, 14));
        filterPanel.add(lblLocTheo);

        String[] filterOptions = {"Tất cả", "Thức ăn", "Đồ uống"};
        JComboBox<String> filterComboBox = new JComboBox<>(filterOptions);
        filterPanel.add(filterComboBox);

        JButton btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(220, 220, 220));
        filterPanel.add(btnClear);

        JButton btnLoc = new JButton("Lọc");
        btnLoc.setBackground(new Color(100, 149, 237));
        btnLoc.setForeground(Color.WHITE);
        filterPanel.add(btnLoc);

        JButton btnXemGioHang = new JButton("Xem giỏ hàng");
        btnXemGioHang.setBackground(new Color(100, 149, 237));
        btnXemGioHang.setForeground(Color.WHITE);
        filterPanel.add(btnXemGioHang);

        headerPanel.add(filterPanel, BorderLayout.CENTER);
        drinkSelectionPanel.add(headerPanel, BorderLayout.NORTH);

        // Item grid
        JPanel itemGridPanel = new JPanel();
        itemGridPanel.setBackground(Color.WHITE);
        itemGridPanel.setLayout(new GridLayout(2, 7, 20, 20));

        String[] items = {
            "Cơm chiên bò", "Cơm chiên trứng", "Nui xào bò", "Mì tôm", "Cá viên chiên", "Mì xào bò",
            "Coca", "Pepsi", "7up", "Nước suối", "Sting", "Redbull"
        };
        String[] itemImageNames = {
            "comchienbo.jpg", "comchientrung.jpg", "nuixaobo.jpg", "mitom.jpg", "cavienchien.jpg", "mixaobo.jpg",
            "coca.jpg", "pepsi.jpg", "7up.jpg", "nuocsuoi.jpg", "sting.jpg", "redbull.jpg"
        };
        String[] itemPrices = {
            "30,000 đ", "30,000 đ", "30,000 đ", "10,000 đ", "15,000 đ", "30,000 đ",
            "15,000 đ", "15,000 đ", "15,000 đ", "10,000 đ", "15,000 đ", "20,000 đ"
        };
        String[] itemTypes = {
            "Thức ăn", "Thức ăn", "Thức ăn", "Thức ăn", "Thức ăn", "Thức ăn",
            "Đồ uống", "Đồ uống", "Đồ uống", "Đồ uống", "Đồ uống", "Đồ uống"
        };

        for (int i = 0; i < items.length; i++) {
            JPanel itemPanel = new JPanel();
            itemPanel.setBackground(Color.WHITE);
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel itemImage = new JLabel();
            try {
                ImageIcon icon = new ImageIcon("E:\\LTJVNC_SLIDE\\KhachHang\\src\\Images\\" + itemImageNames[i]);
                Image scaledImage = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                itemImage.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                System.out.println("Error loading image for " + items[i] + ": " + e.getMessage());
            }
            itemImage.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemPanel.add(itemImage);

            JLabel itemName = new JLabel(items[i], SwingConstants.CENTER);
            itemName.setFont(new Font("Arial", Font.PLAIN, 14));
            itemName.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemPanel.add(itemName);

            JLabel itemPrice = new JLabel(itemPrices[i], SwingConstants.CENTER);
            itemPrice.setFont(new Font("Arial", Font.PLAIN, 12));
            itemPrice.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemPanel.add(itemPrice);

            JPanel quantityPanel = new JPanel();
            quantityPanel.setBackground(Color.WHITE);
            quantityPanel.setLayout(new FlowLayout());

            JButton btnMinus = new JButton("-");
            btnMinus.setPreferredSize(new Dimension(30, 30));
            quantityPanel.add(btnMinus);

            JLabel lblQuantity = new JLabel("0", SwingConstants.CENTER);
            lblQuantity.setPreferredSize(new Dimension(30, 30));
            quantityPanel.add(lblQuantity);

            JButton btnPlus = new JButton("+");
            btnPlus.setPreferredSize(new Dimension(30, 30));
            quantityPanel.add(btnPlus);

            String itemNameStr = items[i];
            btnPlus.addActionListener(e -> {
                int quantity = Integer.parseInt(lblQuantity.getText());
                quantity++;
                lblQuantity.setText(String.valueOf(quantity));
                cart.put(itemNameStr, quantity);
            });

            btnMinus.addActionListener(e -> {
                int quantity = Integer.parseInt(lblQuantity.getText());
                if (quantity > 0) {
                    quantity--;
                    lblQuantity.setText(String.valueOf(quantity));
                    cart.put(itemNameStr, quantity);
                    if (quantity == 0) cart.remove(itemNameStr);
                }
            });

            quantityPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemPanel.add(quantityPanel);

            JButton btnAddToCart = new JButton("Thêm vào giỏ");
            btnAddToCart.setBackground(new Color(100, 149, 237));
            btnAddToCart.setForeground(Color.WHITE);
            btnAddToCart.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemPanel.add(btnAddToCart);

            btnAddToCart.addActionListener(e -> {
                int quantity = Integer.parseInt(lblQuantity.getText());
                if (quantity > 0) {
                    JOptionPane.showMessageDialog(this, "Đã thêm " + quantity + " " + itemNameStr + " vào giỏ hàng!");
                }
            });

            itemPanel.putClientProperty("type", itemTypes[i]);
            itemGridPanel.add(itemPanel);
        }

        JScrollPane itemScrollPane = new JScrollPane(itemGridPanel);
        itemScrollPane.setBorder(null);
        drinkSelectionPanel.add(itemScrollPane, BorderLayout.CENTER);

        // Button actions
        btnClear.addActionListener(e -> {
            for (Component comp : itemGridPanel.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel itemPanel = (JPanel) comp;
                    for (Component subComp : itemPanel.getComponents()) {
                        if (subComp instanceof JPanel) {
                            JPanel quantityPanel = (JPanel) subComp;
                            for (Component qtyComp : quantityPanel.getComponents()) {
                                if (qtyComp instanceof JLabel) {
                                    ((JLabel) qtyComp).setText("0");
                                }
                            }
                        }
                    }
                    comp.setVisible(true);
                }
            }
            cart.clear();
            filterComboBox.setSelectedIndex(0);
        });

        btnLoc.addActionListener(e -> {
            String selectedFilter = (String) filterComboBox.getSelectedItem();
            for (Component comp : itemGridPanel.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel itemPanel = (JPanel) comp;
                    String itemType = (String) itemPanel.getClientProperty("type");
                    if ("Tất cả".equals(selectedFilter) || itemType.equals(selectedFilter)) {
                        itemPanel.setVisible(true);
                    } else {
                        itemPanel.setVisible(false);
                    }
                }
            }
            itemGridPanel.revalidate();
            itemGridPanel.repaint();
        });

        btnXemGioHang.addActionListener(e -> {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Giỏ hàng trống!");
            } else {
                StringBuilder cartDetails = new StringBuilder("Giỏ hàng:\n");
                for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                    if (entry.getValue() > 0) {
                        cartDetails.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                    }
                }
                JOptionPane.showMessageDialog(this, cartDetails.toString());
            }
        });

        // Chơi game panel
        JPanel gamePanel = new JPanel();
        gamePanel.setBackground(Color.WHITE);
        gamePanel.setLayout(new BorderLayout());

        // Header for Chơi game panel
        JPanel gameHeaderPanel = new JPanel();
        gameHeaderPanel.setBackground(Color.WHITE);
        gameHeaderPanel.setLayout(new BorderLayout());

        JLabel lblChoiGame = new JLabel("Chơi game", SwingConstants.LEFT);
        lblChoiGame.setFont(new Font("Arial", Font.BOLD, 24));
        gameHeaderPanel.add(lblChoiGame, BorderLayout.NORTH);

        JPanel gameFilterPanel = new JPanel();
        gameFilterPanel.setBackground(Color.WHITE);
        gameFilterPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JLabel lblGameFilter = new JLabel("Lọc theo thể loại:");
        lblGameFilter.setFont(new Font("Arial", Font.PLAIN, 14));
        gameFilterPanel.add(lblGameFilter);

        String[] gameFilterOptions = {"Tất cả", "MOBA", "FPS", "Thể thao"};
        JComboBox<String> gameFilterComboBox = new JComboBox<>(gameFilterOptions);
        gameFilterPanel.add(gameFilterComboBox);

        JButton btnBack = new JButton("Quay lại");
        btnBack.setBackground(new Color(100, 149, 237));
        btnBack.setForeground(Color.WHITE);
        gameFilterPanel.add(btnBack);

        gameHeaderPanel.add(gameFilterPanel, BorderLayout.CENTER);
        gamePanel.add(gameHeaderPanel, BorderLayout.NORTH);

        // Usage information
        JPanel usagePanel = new JPanel();
        usagePanel.setBackground(Color.WHITE);
        usagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        usagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblUsageInfo = new JLabel("Thời gian chơi: 2 giờ | Số dư: 50,000 VNĐ | Máy: Máy 5");
        lblUsageInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        usagePanel.add(lblUsageInfo);

        lblTimeRemaining = new JLabel("Thời gian còn lại: 02:00:00", SwingConstants.CENTER);
        lblTimeRemaining.setFont(new Font("Arial", Font.BOLD, 16));
        usagePanel.add(lblTimeRemaining);

        JButton btnExtendSession = new JButton("Gia hạn thêm");
        btnExtendSession.setBackground(new Color(50, 205, 50));
        btnExtendSession.setForeground(Color.WHITE);
        usagePanel.add(btnExtendSession);

        JButton btnEndSession = new JButton("Kết thúc phiên");
        btnEndSession.setBackground(new Color(255, 99, 71));
        btnEndSession.setForeground(Color.WHITE);
        usagePanel.add(btnEndSession);

        gamePanel.add(usagePanel, BorderLayout.SOUTH);

        // Khởi tạo Timer để đếm ngược
        startTimer();

        // Game selection grid
        JPanel gameGridPanel = new JPanel();
        gameGridPanel.setBackground(Color.WHITE);
        gameGridPanel.setLayout(new GridLayout(0, 5, 20, 20));

        String[] games = {"League of Legends", "PUBG", "FIFA 23", "CS:GO", "Dota 2", "Valorant", "GTA V", "Minecraft"};
        String[] gameImageNames = {"lol.jpg", "pubg.jpg", "fifa.jpg", "csgo.jpg", "dota.jpg", "valorant.jpg", "gtav.jpg", "minecraft.jpg"};
        String[] gameGenres = {"MOBA", "FPS", "Thể thao", "FPS", "MOBA", "FPS", "Hành động", "Sandbox"};

        for (int i = 0; i < games.length; i++) {
            JPanel gameItemPanel = new JPanel();
            gameItemPanel.setBackground(Color.WHITE);
            gameItemPanel.setLayout(new BoxLayout(gameItemPanel, BoxLayout.Y_AXIS));
            gameItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel gameImage = new JLabel();
            try {
                ImageIcon icon = new ImageIcon("E:\\LTJVNC_SLIDE\\KhachHang\\src\\Images\\" + gameImageNames[i]);
                Image scaledImage = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                gameImage.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                System.out.println("Error loading image for " + games[i] + ": " + e.getMessage());
            }
            gameImage.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameItemPanel.add(gameImage);

            JLabel gameName = new JLabel(games[i], SwingConstants.CENTER);
            gameName.setFont(new Font("Arial", Font.PLAIN, 14));
            gameName.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameItemPanel.add(gameName);

            JButton btnPlay = new JButton("Chơi");
            btnPlay.setBackground(new Color(100, 149, 237));
            btnPlay.setForeground(Color.WHITE);
            btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameItemPanel.add(btnPlay);

            String gameNameStr = games[i];
            btnPlay.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Đang mở " + gameNameStr + "...");
            });

            gameItemPanel.putClientProperty("genre", gameGenres[i]);
            gameGridPanel.add(gameItemPanel);
        }

        JScrollPane gameScrollPane = new JScrollPane(gameGridPanel);
        gameScrollPane.setBorder(null);
        gamePanel.add(gameScrollPane, BorderLayout.CENTER);

        // Button actions for Chơi game panel
        btnBack.addActionListener(e -> {
            splitPane.setRightComponent(mainPanel);
            revalidate();
            repaint();
        });

        btnEndSession.addActionListener(e -> {
            timer.cancel();
            JOptionPane.showMessageDialog(this, "Phiên chơi đã kết thúc!");
            splitPane.setRightComponent(mainPanel);
            revalidate();
            repaint();
        });

        btnExtendSession.addActionListener(e -> {
            showExtendSessionDialog();
        });

        gameFilterComboBox.addActionListener(e -> {
            String selectedFilter = (String) gameFilterComboBox.getSelectedItem();
            for (Component comp : gameGridPanel.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel gameItemPanel = (JPanel) comp;
                    String gameGenre = (String) gameItemPanel.getClientProperty("genre");
                    if ("Tất cả".equals(selectedFilter) || gameGenre.equals(selectedFilter)) {
                        gameItemPanel.setVisible(true);
                    } else {
                        gameItemPanel.setVisible(false);
                    }
                }
            }
            gameGridPanel.revalidate();
            gameGridPanel.repaint();
        });

        // Create JSplitPane to hold sidebar and content
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebarPanel, mainPanel);
        splitPane.setDividerSize(5);
        splitPane.setDividerLocation(sidebarWidth);
        splitPane.setResizeWeight(0);
        splitPane.setContinuousLayout(true);

        // Add JSplitPane to frame
        add(splitPane, BorderLayout.CENTER);

        // Tree selection listener
        menuTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) menuTree.getLastSelectedPathComponent();
            if (selectedNode == null) return;

            String selected = selectedNode.getUserObject().toString();
            if ("Chọn món".equals(selected)) {
                splitPane.setRightComponent(drinkSelectionPanel);
            } else if ("Chơi game".equals(selected)) {
                splitPane.setRightComponent(gamePanel);
            } else if ("Hóa đơn".equals(selected)) {
                JOptionPane.showMessageDialog(this, "Chuyển đến màn hình hóa đơn!");
                splitPane.setRightComponent(mainPanel);
            } else if ("Đăng xuất".equals(selected)) {
                timer.cancel();
                dispose();
            }
            revalidate();
            repaint();
        });

        // Trang chủ label listener
        Trangchu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                splitPane.setRightComponent(mainPanel);
                revalidate();
                repaint();
            }
        });
    }

    // Phương thức khởi động Timer đếm ngược
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (timeRemainingSeconds <= 0) {
                        timer.cancel();
                        JOptionPane.showMessageDialog(GiaoDienKhachHang.this, "Hết thời gian chơi! Vui lòng gia hạn hoặc kết thúc phiên.");
                        splitPane.setRightComponent(mainPanel);
                        revalidate();
                        repaint();
                        return;
                    }

                    // Cập nhật thời gian còn lại
                    long hours = timeRemainingSeconds / 3600;
                    long minutes = (timeRemainingSeconds % 3600) / 60;
                    long seconds = timeRemainingSeconds % 60;
                    lblTimeRemaining.setText(String.format("Thời gian còn lại: %02d:%02d:%02d", hours, minutes, seconds));

                    // Thông báo khi còn 5 phút
                    if (timeRemainingSeconds <= 300 && !warningShown) {
                        JOptionPane.showMessageDialog(GiaoDienKhachHang.this, "Thời gian chơi còn 5 phút! Vui lòng gia hạn để tiếp tục.");
                        warningShown = true;
                    }

                    timeRemainingSeconds--;
                });
            }
        }, 0, 1000);
    }

    // Phương thức hiển thị dialog gia hạn
    private void showExtendSessionDialog() {
        JDialog extendDialog = new JDialog(this, "Gia hạn thời gian chơi", true);
        extendDialog.setSize(400, 300);
        extendDialog.setLocationRelativeTo(this);
        extendDialog.setLayout(new BorderLayout(10, 10));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblInstruction = new JLabel("Chọn gói gia hạn hoặc nạp tiền:");
        lblInstruction.setFont(new Font("Arial", Font.BOLD, 16));
        lblInstruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblInstruction);
        contentPanel.add(Box.createVerticalStrut(20));

        String[] packages = {"30 phút (10,000 VNĐ)", "1 giờ (20,000 VNĐ)", "2 giờ (35,000 VNĐ)"};
        JComboBox<String> packageComboBox = new JComboBox<>(packages);
        packageComboBox.setMaximumSize(new Dimension(300, 30));
        packageComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(packageComboBox);
        contentPanel.add(Box.createVerticalStrut(20));

        JLabel lblCustomAmount = new JLabel("Hoặc nhập số tiền (VNĐ):");
        lblCustomAmount.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCustomAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblCustomAmount);

        JTextField txtCustomAmount = new JTextField();
        txtCustomAmount.setMaximumSize(new Dimension(300, 30));
        txtCustomAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(txtCustomAmount);
        contentPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.setBackground(new Color(100, 149, 237));
        btnConfirm.setForeground(Color.WHITE);
        buttonPanel.add(btnConfirm);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setBackground(new Color(220, 220, 220));
        buttonPanel.add(btnCancel);

        extendDialog.add(contentPanel, BorderLayout.CENTER);
        extendDialog.add(buttonPanel, BorderLayout.SOUTH);

        btnConfirm.addActionListener(e -> {
            try {
                long additionalSeconds = 0;
                String selectedPackage = (String) packageComboBox.getSelectedItem();
                if (!txtCustomAmount.getText().trim().isEmpty()) {
                    double amount = Double.parseDouble(txtCustomAmount.getText().trim());
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(this, "Số tiền phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Giả sử 1000 VNĐ = 3 phút (180 giây)
                    additionalSeconds = (long) (amount / 1000 * 180);
                } else if (selectedPackage != null) {
                    if (selectedPackage.contains("30 phút")) {
                        additionalSeconds = 30 * 60;
                    } else if (selectedPackage.contains("1 giờ")) {
                        additionalSeconds = 60 * 60;
                    } else if (selectedPackage.contains("2 giờ")) {
                        additionalSeconds = 2 * 60 * 60;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn gói hoặc nhập số tiền!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                timeRemainingSeconds += additionalSeconds;
                warningShown = false; // Reset thông báo
                JOptionPane.showMessageDialog(this, "Đã gia hạn thêm thời gian chơi!");
                extendDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> extendDialog.dispose());

        extendDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GiaoDienKhachHang frame = new GiaoDienKhachHang();
            frame.setVisible(true);
        });
    }
}