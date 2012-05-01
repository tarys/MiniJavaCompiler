/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import nametable.entries.Entry;
import nametable.entries.ProgramEntry;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.Vector;

/**
 * @author Taras
 */
public class NameTableJTreeModel implements TreeModel {
    private Vector<TreeModelListener> treeModelListeners = new Vector<TreeModelListener>();
    private Entry rootEntry;

    public NameTableJTreeModel(ProgramEntry rootEntry) {
        this.rootEntry = rootEntry;
    }

    /**
     * The only event raised by this model is TreeStructureChanged with the
     * root as path, i.e. the whole tree has changed.
     */
    protected void fireTreeStructureChanged(Entry oldRoot) {
        int len = treeModelListeners.size();
        TreeModelEvent e = new TreeModelEvent(this,
                new Object[]{oldRoot});
        for (TreeModelListener tml : treeModelListeners) {
            tml.treeStructureChanged(e);
        }
    }

    //////////////// TreeModel interface implementation ///////////////////////
    @Override
    public Object getRoot() {
        return rootEntry;
    }

    @Override
    public Object getChild(Object o, int index) {
        Entry parent = (Entry) o;
        return parent.getChildren().get(index);
    }

    @Override
    public int getChildCount(Object o) {
        Entry parent = (Entry) o;
        return parent.getChildren().size();
    }

    @Override
    public boolean isLeaf(Object o) {
        Entry entry = (Entry) o;
        return ((entry.getChildren() == null) || (entry.getChildren().isEmpty()));
    }


    @Override
    public int getIndexOfChild(Object p, Object c) {
        Entry parent = (Entry) p;
        Entry child = (Entry) c;
        return parent.getChildren().indexOf(child);
    }

    @Override
    public void addTreeModelListener(TreeModelListener listener) {
        treeModelListeners.addElement(listener);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener listener) {
        treeModelListeners.removeElement(listener);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        /**
         * Messaged when the user has altered the value for the item
         * identified by path to newValue.  Not used by this model.
         */
    }

}
