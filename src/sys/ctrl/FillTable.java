package sys.ctrl;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

/**
 * @author CK Seong
 */

/*
Courtesy of Paul Vargas on Stack Overflow:
http://stackoverflow.com/questions/10620448/most-simple-code-to-populate-jtable-from-resultset

Modified for better handling and for my own need.

Building a table set with custom db's data is as simple as:
Table.setModel(new FillTable().tbModel(query);
 */
public class FillTable {
    private String query;

    public FillTable() {
    }

    public DefaultTableModel tbModel(String query) throws SQLException {
        this.query = query;
        DBConnection db = new DBConnection("DAT.ssb");
        ResultSet rs = db.executeQuery(this.query);
        //set column names
        ResultSetMetaData md = rs.getMetaData();
        Vector<String> col = new Vector<>();
        int colCount = md.getColumnCount();

        for (int i = 1; i <= colCount; i++) {
            col.add(md.getColumnName(i));
        }

        col.ensureCapacity(colCount);

        Vector<Vector<Object>> row = new Vector<>();

        while ((rs.next())) {
            Vector<Object> rowData = new Vector<>();
            for (int i = 1; i <= colCount; i++) {
                rowData.add(rs.getObject(i));
            }
            row.add(rowData);
        }

        db.closeCon();

        return new DefaultTableModel(row, col);
    }

    //for purchase req table in create PR
    public DefaultTableModel modelPR() {

        return new DefaultTableModel(
                new Object[][]{
                },
                new String[]{"Item Code", "Item Name", "Quantity", "Amount"}
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }

    //for viewing purchase req table in view PR
    public DefaultTableModel modelPRView() {
        return new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "PR No", "Generated By", "Req Date", "Item Code", "Quantity", "Amount"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }

    //for Generate PO's table
    public DefaultTableModel modelPOGenerate() {
        return new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "PR No", "Created By", "Created Date", "Item Code", "Item Name",
                        "Supplied by", "Unit Price", "Quantity", "Amount"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }

    //for LPO's table
    public DefaultTableModel modelPOList() {
        return new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "PO No", "Generated Date", "Item Code", "Item Name", "Supplied by",
                        "Unit Price", "Quantity", "Amount"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }
}
