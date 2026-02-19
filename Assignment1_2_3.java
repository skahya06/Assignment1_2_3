import java.util.*;

public class Assignment1_2_3 {

    // -------------------------
    // Node + Basic Linked List Ops
    // -------------------------
    static class Node {
        int key;
        Node next;

        Node(int key) {
            this.key = key;
            this.next = null;
        }
    }

    // LIST-INSERT(L, x): insert x at head, return new head
    static Node listInsert(Node head, Node x) {
        x.next = head;
        return x;
    }

    // LIST-SEARCH(L, k): return node with key k, else null
    static Node listSearch(Node head, int key) {
        Node cur = head;
        while (cur != null) {
            if (cur.key == key) return cur;
            cur = cur.next;
        }
        return null;
    }

    // LIST-DELETE(L, x): delete given node reference x, return (possibly new) head
    static Node listDelete(Node head, Node x) {
        if (head == null || x == null) return head;

        // if x is head
        if (head == x) {
            return head.next;
        }

        Node prev = head;
        while (prev.next != null && prev.next != x) {
            prev = prev.next;
        }

        // if found, unlink it
        if (prev.next == x) {
            prev.next = x.next;
            x.next = null; // optional: detach
        }

        return head;
    }

    static void printList(Node head) {
        Node cur = head;
        while (cur != null) {
            System.out.print(cur.key);
            if (cur.next != null) System.out.print(" -> ");
            cur = cur.next;
        }
        System.out.println();
    }

    // "populate" style: insert multiple nodes (head insert, like in class demos)
    static Node populate(int... keys) {
        Node head = null;
        for (int k : keys) {
            head = listInsert(head, new Node(k));
        }
        return head;
    }
