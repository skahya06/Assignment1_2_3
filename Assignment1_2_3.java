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

// -------------------------
    // Assignment 2: Palindrome Linked List
    // O(n) time, O(1) extra space
    // -------------------------
    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;

        // 1) find middle (slow/fast)
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // if odd length, skip the middle node
        if (fast != null) { // odd
            slow = slow.next;
        }

        // 2) reverse second half
        Node secondHalf = reverseList(slow);

        // 3) compare first half and reversed second half
        Node p1 = head;
        Node p2 = secondHalf;
        boolean ok = true;
        while (p2 != null) { // only need to compare length of second half
            if (p1.key != p2.key) {
                ok = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // 4) (optional but good practice) restore list
        reverseList(secondHalf);

        return ok;
    }

    static Node reverseList(Node head) {
        Node prev = null;
        Node cur = head;
        while (cur != null) {
            Node nxt = cur.next;
            cur.next = prev;
            prev = cur;
            cur = nxt;
        }
        return prev;
    }
    
    // -------------------------
    // Assignment 3: Trapped Rainwater
    // Two-pointer O(n) time, O(1) space
    // -------------------------
    static int trapRainwater(int[] height) {
        if (height == null || height.length < 3) return 0;

        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int water = 0;

        while (left < right) {
            if (height[left] <= height[right]) {
                if (height[left] >= leftMax) leftMax = height[left];
                else water += (leftMax - height[left]);
                left++;
            } else {
                if (height[right] >= rightMax) rightMax = height[right];
                else water += (rightMax - height[right]);
                right--;
            }
        }
        return water;
    }
// -------------------------
    // Main demo (matches assignment steps)
    // -------------------------
    public static void main(String[] args) {

        // ===== Assignment 1 Demo =====
        System.out.println("=== Assignment 1: Basic Linked List Ops ===");

        // Create empty list
        Node head = null;

        // Insert at least three nodes (using populate method style)
        // Note: populate does head-insert in this order.
        head = populate(10, 20, 30); // results: 30 -> 20 -> 10

        System.out.print("List after inserts: ");
        printList(head);

        // Search for a node
        int target = 20;
        Node found = listSearch(head, target);
        System.out.println("Search for " + target + ": " + (found != null ? "FOUND" : "NOT FOUND"));

        // Delete one node by passing node reference (delete the found node)
        head = listDelete(head, found);

        System.out.print("List after deleting node " + target + ": ");
        printList(head);

        // ===== Assignment 2 Demo =====
        System.out.println("\n=== Assignment 2: Palindrome Linked List ===");

        Node p1 = populate(1, 2, 3, 2, 1); // will print as 1 -> 2 -> 3 -> 2 -> 1? careful: populate head-inserts
        // Because populate uses head-insert, populate(1,2,3,2,1) produces 1->2->3->2->1 (palindrome) only if inserted reversed.
        // So we’ll build explicitly to match examples:
        Node palA = null;
        palA = listInsert(palA, new Node(1));
        palA = listInsert(palA, new Node(2));
        palA = listInsert(palA, new Node(3));
        palA = listInsert(palA, new Node(2));
        palA = listInsert(palA, new Node(1)); // palA is 1->2->3->2->1

        System.out.print("List: ");
        printList(palA);
        System.out.println("Palindrome? " + isPalindrome(palA));

        Node palB = null;
        palB = listInsert(palB, new Node(10));
        palB = listInsert(palB, new Node(20));
        palB = listInsert(palB, new Node(20));
        palB = listInsert(palB, new Node(10)); // 10->20->20->10

        System.out.print("List: ");
        printList(palB);
        System.out.println("Palindrome? " + isPalindrome(palB));

        Node notPal = null;
        notPal = listInsert(notPal, new Node(5));
        notPal = listInsert(notPal, new Node(10));
        notPal = listInsert(notPal, new Node(17)); // 17->10->5 (not palindrome)

        System.out.print("List: ");
        printList(notPal);
        System.out.println("Palindrome? " + isPalindrome(notPal));

        // ===== Assignment 3 Demo =====
        System.out.println("\n=== Assignment 3: Trapped Rainwater ===");

        int[] elevation = {1, 2, 1, 4, 1, 2, 1, 5, 0, 0, 2, 1, 5};
        int trapped = trapRainwater(elevation);

        System.out.println("Elevation: " + Arrays.toString(elevation));
        System.out.println("Trapped water = " + trapped + " units");
         }
}