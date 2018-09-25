import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.*;

class Node {

    int x, y, z, i;
    Node next, prev;

    Node(int x, int y, int z, int i) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.i = i;

    }
}

class List {

    Node head = null;
    Node tail = null;

    Node merge(Node left, Node right) {
        Node ans = null; ans=new Node(-1,-1,-1,-1);
        Node ptr = ans;
        
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        boolean chk=true;
        while(left!=null && right!=null){
            
        if (left.x > right.x) {
            ans.next=left;
            left=left.next;
            ans=ans.next;
            
        } else {
            ans.next=right;
            right=right.next;
            ans=ans.next;
        }  
            
        }
        
        while(left!=null){
              ans.next = left;
            left=left.next;
            ans=ans.next;
        }
           while(right!=null){
              ans.next = right;
            right=right.next;
            ans=ans.next;
        }
        
        
      
        return ptr.next;
    }

    Node merge_sort(Node n) {

        if (n == null || n.next == null) {
            return n;
        }

        Node mid = get_mid(n);
        Node temp = mid.next;
        mid.next = null;

        Node left = merge_sort(n);
        Node right = merge_sort(temp);

        Node merge = merge(left, right);

        return merge;
    }

    Node get_mid(Node n) {

        if (n == null) {
            return n;
        }

        Node fptr = n.next;
        Node sptr = n;

        while (fptr != null) {
            fptr = fptr.next;
            if (fptr != null) {
                sptr = sptr.next;
                fptr = fptr.next;
            }
        }
        return sptr;

    }

}

class Node2 {

    int x;
    int y;
    int z;
    int i;
    Node2 left;
    Node2 right;
    int height;

    public Node2(int y, int z, int x, int i) {
        this.y = y;
        this.z = z;
        this.x = x;
        this.i = i;
        height = 1;
    }
}

class AvlTree {

    int anscount = 0;
    Node ans;
    Node headw;

    public int getBalance(Node2 n) {
        if (n != null) {
            return (getHeight(n.left) - getHeight(n.right));
        }
        return 0;
    }

    public int getHeight(Node2 n) {
        if (n != null) {
            return n.height;
        }
        return 0;
    }

    public Node2 rightRotate(Node2 y) {
        Node2 x = y.left;
        Node2 T2 = x.right;

        x.right = y;
        y.left = T2;

        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return x;
    }

    public Node2 leftRotate(Node2 x){
        Node2 y = x.right;
        Node2 T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }

    public Node2 insert(Node2 node, int y, int z, int x, int i) {

        if (node == null) {

            return (new Node2(y, z, x, i));
        } else if (y > node.y) {
            node.right = insert(node.right, y, z, x, i);
        } else if (y < node.y) {
            node.left = insert(node.left, y, z, x, i);
        } else {
            return node;
        }

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

        int balDiff = getBalance(node);
/*
        if (balDiff > 1 && y < node.left.y) {
            return rightRotate(node);
        }

        if (balDiff < -1 && y > node.right.y) {
            return leftRotate(node);
        }

        if (balDiff > 1 && y > node.left.y) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balDiff < -1 && y < node.right.y) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
*/

        if (balDiff > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }

        if (balDiff > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balDiff < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }

        if (balDiff < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    Node2 deleteNode(Node2 root, int key) {

        if (root == null) {
            return root;
        }

        if (key < root.y) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.y) {
            root.right = deleteNode(root.right, key);
        } else {

            if ((root.left == null) || (root.right == null)) {
                Node2 temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {

                Node2 temp = minValueNode(root.right);

                root.y = temp.y;
                root.i = temp.i;
                root.x = temp.x;
                root.z = temp.z;

                root.right = deleteNode(root.right, temp.y);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    Node2 minValueNode(Node2 node) {
        Node2 current = node;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    boolean checkk = true;

    public Node2 findNode(Node2 node, int y, int z){
        
        if(node!=null && node.left==null && node.right==null) return node;
        
        if(node!=null && y>node.y && z<node.z) return findNode(node.right,y,z);
        if(node!=null && y<node.y && z>node.z) return findNode(node.left,y,z);
        
        return node;
    }
    
    
    public Node2 insert2(Node2 node, int y, int z, int x, int i) {

        ArrayList<Integer> al = new ArrayList<>();

        Node2 temppp=findNode(node,y,z);
        inorder_s(al,temppp , y, z, x, i);

        
        for (Integer m : al) {
            node = deleteNode(node, m);

        }

        if (checkk) {
           

            node = insert(node, y, z, x, i);
        }
        checkk = true;
        return node;
    }

    public void inorder(Node2 root) {
        if (root != null) {
            inorder(root.left);
            anscount++;
            ans.next = new Node(-root.i, root.x, root.y, root.z);
            ans = ans.next;
            inorder(root.right);

        }
    }

    public void inorder_s(ArrayList<Integer> al, Node2 root, int y, int z, int x, int i) {
        if (root != null) {
            inorder_s(al, root.left, y, z, x, i);

            if (y >= root.y && z >= root.z) {
                al.add(root.y);
               
                if (root.x != x) {
                    ans.next = new Node(-root.i, root.x, root.y, root.z);
                    ans = ans.next;
                    anscount++;
                }
            } else if (y <= root.y && z <= root.z) {
                checkk = false;
            }
            inorder_s(al, root.right, y, z, x, i);
        }
    }

}

class NewClass{

    public static void main(String args[]) throws IOException{

        Scanner sc = new Scanner(new FileReader(args[0]));
        PrintWriter pw=new PrintWriter(new FileWriter(args[1]));
       //Scanner sc=new Scanner(System.in);
        int n = sc.nextInt();

        List obj = new List();

        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int z = sc.nextInt();

            if (i == 0) {

                obj.head = new Node(x, y, z, i);
                obj.tail = obj.head;
            } else {
                obj.tail.next = new Node(x, y, z, i);
                obj.tail.next.prev = obj.tail;
                obj.tail = obj.tail.next;

            }
           //System.out.println(i);
        }
      //  System.out.println("ok");
        obj.head = new List().merge_sort(obj.head);
        //System.out.println("i");
        Node tempp = null;
        if (obj.head != null) {
            tempp = obj.head;
        }

        AvlTree avl = new AvlTree();
        avl.ans = new Node(-1, -1, -1, -1);
        avl.headw = avl.ans;
        Node2 headavl = null;

        while (tempp != null) {
            headavl = avl.insert2(headavl, tempp.y, tempp.z, tempp.x, tempp.i);
            tempp = tempp.next;//System.out.println("n");
        }
        //System.out.println("y");
        avl.inorder(headavl);

        pw.println(avl.anscount);
        avl.headw = obj.merge_sort(avl.headw.next);
        pw.flush();
        while (avl.headw != null) {
            pw.println(-avl.headw.x); pw.flush();
            avl.headw = avl.headw.next;
        } pw.close();

    }

}

