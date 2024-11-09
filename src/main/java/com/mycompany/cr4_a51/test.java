/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cr4_a51;

/**
 *
 * @author ADMIN
 */
public class test {

    private byte[] S = new byte[256];
    private int x = 0;
    private int y = 0;

    // Khởi tạo RC4 với khóa
    public test(byte[] key) {
        init(key);
    }

    // Khởi tạo mảng S dựa trên khóa
    private void init(byte[] key) {
        int keyLength = key.length;

        // Khởi tạo mảng S
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
        }

        int j = 0;
        // Hoán vị mảng S dựa trên khóa
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + key[i % keyLength]) & 0xFF;
            byte temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }
    }

    // Tạo một byte keystream tiếp theo
    private byte keyStream() {
        x = (x + 1) & 0xFF;
        y = (y + S[x]) & 0xFF;

        // Hoán vị S[x] và S[y]
        byte temp = S[x];
        S[x] = S[y];
        S[y] = temp;

        // Trả về giá trị keystream tiếp theo
        return S[(S[x] + S[y]) & 0xFF];
    }

    // Mã hóa hoặc giải mã văn bản
    public byte[] process(byte[] text) {
        byte[] result = new byte[text.length];
        for (int i = 0; i < text.length; i++) {
            result[i] = (byte) (text[i] ^ keyStream()); // XOR với keystream
        }
        return result;
    }

    public static void main(String[] args) {
        String key = "mysecretkey";
        String plaintext = "Hello, RC4!";

        // Chuyển đổi chuỗi thành mảng byte
        byte[] keyBytes = key.getBytes();
        byte[] plaintextBytes = plaintext.getBytes();

        // Khởi tạo RC4 với khóa
        test rc4 = new test(keyBytes);

        // Mã hóa văn bản
        byte[] encrypted = rc4.process(plaintextBytes);
        System.out.println("Van ban ma hoa: " + new String(encrypted));

        // Khởi tạo lại RC4 với cùng khóa để giải mã
        rc4 = new test(keyBytes);
        byte[] decrypted = rc4.process(encrypted);
        System.out.println("Van ban giai ma: " + new String(decrypted));
    }
}

