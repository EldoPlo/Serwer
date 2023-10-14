package com.journaldev.socket;

import java.util.Deque;
import java.util.LinkedList;

public class Tree< T extends Comparable<T>>
{
    private Node root;

    private class Node
    {

        T data;
        Node right;
        Node left;
        Node parent;
        T value;
        public Node(T data)
        {
            this.data = data;
            left = null;
            right = null;
        }
    }

    public void insert(T data)
    {
        root = insertNode(root, data);
    }

    public Node insertNode (Node root, T data)
    {
        Node newNode = new Node(data);
        Node current = root;
        Node partent = null;

        if(root == null)
        {
            root = newNode;
            newNode.parent = null;
            return root;
        }

        while (current != null)
        {
            partent = current;

            int comparevalue = data.compareTo(current.data);

            if (comparevalue < 0)
            {
                current = current.left;
            }
            else if (comparevalue > 0)
            {
                current = current.right;
            }
            else
            {
                return root;
            }
        }

        newNode.parent = partent;

        if(data.compareTo(partent.data) < 0)
        {
            partent.left = newNode;
        }
        else
        {
            partent.right = newNode;
        }

        return root;
    }

    public boolean searchNode( T value)
    {
       Node current = root;

       while (current != null)
       {
           int comparevalue1 = value.compareTo(current.data);
           if(comparevalue1 == 0)
           {
               return true;
           }
           else if (comparevalue1 < 0)
           {
               current = current.left;
           }
           else
           {
               current = current.right;
           }
       }
       return false;
    }

    public String IterativeinOrder() {
        if (root == null) {
            return "";
        }

        StringBuilder treeString = new StringBuilder();
        Deque<Node> deque = new LinkedList<>();
        Node current = root;
        while (current != null || deque.size() > 0) {
            while (current != null) {
                deque.push(current);
                treeString.append("( ");
                current = current.left;
            }

            current = deque.pop();
            treeString.append("{ ").append(current.data).append(" }");
            current = current.right;
            treeString.append(" )");
        }

        return treeString.toString();
    }

    public void delete(T value)
    {
        root = deleteNode(root,value);
    }

    public Node deleteNode(Node root, T value) {
        if (root == null) {
            return null;
        }

        Node parent = null;
        Node current = root;

        while (current != null) {
            int compareValue = value.compareTo(current.data);

            if (compareValue == 0) {
                break;
            }

            parent = current;

            if (compareValue < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (current == null)
        {
            return root;
        }


        if (current.left == null && current.right == null)
        {
            // Przypadek 1: Węzeł nie ma dzieci
            if (parent == null) {
                return null;
            }

            if (current == parent.left) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        else if (current.left == null || current.right == null)
        {
            Node child = (current.left != null) ? current.left : current.right;

            if (parent == null) {
                return child;
            }

            if (current == parent.left) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        }

        else
        {
            Node successorParent = current;
            Node successor = current.right;

            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            current.data = successor.data;

            if (successorParent.left == successor) {
                successorParent.left = successor.right;
            } else {
                successorParent.right = successor.right;
            }
        }

        return root;
    }

}
