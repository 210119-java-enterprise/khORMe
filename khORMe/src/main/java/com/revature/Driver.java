package com.revature;

import java.util.List;
import com.revature.forum.models.Boards;
import com.revature.util.ColumnField;
import com.revature.util.KeyField;
import com.revature.util.Metamodel;


/**
 *       khORMe
 * code for the code gods
 */
public class Driver {
    public static void main(String[] args) {
        Metamodel<Boards> userMetamodel = Metamodel.of(Boards.class);

        KeyField keyField = userMetamodel.getPrimaryKey();
        List<ColumnField> columnFields = userMetamodel.getColumns();

        System.out.printf("The primary key of User is: %s\n",keyField.getName());

        for(ColumnField columnField :columnFields) {
            System.out.printf("The User class contains a column called: %s\n", columnField.getName());
        }
    }
}

