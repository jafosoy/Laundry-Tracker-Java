package gui.locations.closetspecificmenubuttons;

import model.clothes.Clothing;
import model.locations.LaundryLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

public class CategoryDropDown extends JPanel implements ActionListener {

    private JComboBox categoryChooser;
    private String[] updatedCategories;
    private String categoryChosen = "---";
    private JLabel field;
    private List<Clothing> allClothes;
    private LaundryLocation userCloset;

    public CategoryDropDown(LaundryLocation userCloset) {
        super(new GridLayout(1, 2));

        this.userCloset = userCloset;

        List<String> categories = userCloset.getMyWardrobe().getCategoryNames();
        categories.add(0, "---");
        updatedCategories = categories.toArray(new String[0]);
        categoryChooser = new JComboBox(updatedCategories);
        categoryChooser.setSelectedIndex(0);
        categoryChooser.addActionListener(this);
        field = new JLabel("Category");
        field.setHorizontalAlignment(JLabel.CENTER);
        this.add(field);
        this.add(categoryChooser);
        this.setVisible(true);

    }

    // EFFECTS: getter for category chosen
    public String getCategoryChosen() {
        return this.categoryChosen;
    }

    // EFFECTS: getter for all clothes of type category chosen
    public List<Clothing> getAllClothes() {
        if (!categoryChosen.equals("---")) {
            return this.allClothes;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        categoryChosen = (String) cb.getSelectedItem();
        allClothes = userCloset.getMyWardrobe().getClothes(userCloset.getMyWardrobe().getCategory(categoryChosen));
    }
}
