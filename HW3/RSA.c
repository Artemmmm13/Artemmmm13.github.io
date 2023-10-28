#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <time.h>
#define BITS 16

// KEY GENERATION
uint64_t gcd(uint64_t a, uint64_t b) {
    if (a == 0) return b;
    return gcd(b % a, a);
}


uint64_t lcm(uint64_t a, uint64_t b){
    uint64_t GCD = gcd(a, b);
    return (a*b) / GCD;
}

uint64_t mod_pow(uint64_t base, uint64_t exponent, uint64_t modulus) {
    uint64_t result = 1;
    base = base % modulus;
    while (exponent > 0) {
        if (exponent % 2 == 1)
            result = (result * base) % modulus;
        exponent = exponent >> 1;
        base = (base * base) % modulus;
    }
    return result;
}

bool is_prime(uint64_t n, int k) {
    if (n <= 1  (n > 2 && n % 2 == 0))
        return false;

    if (n == 2  n == 3)
        return true;

    uint64_t d = n - 1;
    int s = 0;

    while (d % 2 == 0) {
        d /= 2;
        s++;
    }

    for (int i = 0; i < k; i++) {
        uint64_t a = 2 + rand() % (n - 3);
        uint64_t x = mod_pow(a, d, n);

        if (x == 1 || x == n - 1)
            continue;

        for (int j = 0; j < s - 1; j++) {
            x = mod_pow(x, 2, n);
            if (x == 1)
                return false;
            if (x == n - 1)
                break;
        }

        if (x != n - 1)
            return false;
    }

    return true;
}

uint32_t generate_prime(int seconds) {
    srand((unsigned int)time(NULL) + seconds);

    uint32_t min = 1ULL << (BITS- 1);
    uint32_t max = (1ULL << BITS) - 1;


    while (1) {
        uint64_t candidate = min + rand() % (max - min + 1);
        if (is_prime(candidate, 10)) {
            return candidate;
        }
    }
}

uint64_t generate_e(uint64_t lcm) {
    uint8_t e = 2;

    while (1){
        if (gcd(e, lcm) == 1){
            return e;
        }
        e++;
    }
}


uint64_t generate_d(uint64_t e, uint64_t lcm){
    uint64_t d ;

    for (d = 2; d < lcm; d++){
        if ( ((d*e) % lcm) == 1){
            return d;
        }
    }

    return -1;
}

// ENCRYPTION

uint64_t encrypt_msg(uint64_t n, uint64_t e, uint64_t msg){
    return mod_pow(msg, e, n);
}

// DECRYPTION

uint64_t decrypt_msg(uint64_t msg, uint64_t d, uint64_t n){
    return mod_pow(msg, d, n);
}


// BRUTE FORCE DECRYPTION


// a method to find p and q from n
uint64_t*  find_p_and_q(uint64_t n){
    uint64_t* p_and_q = (uint64_t *)malloc(2 * sizeof(uint64_t));
    uint64_t p = 2;
    while (1){
        if (n % p == 0){
            uint64_t q = n / p;
            if (is_prime(q, 10)){
                p_and_q[0] = p;
                p_and_q[1] = q;
                break;
            }
        }
        p++;
    }
    return p_and_q;
}

// DRIVER CODE
int main() {
    uint64_t p = generate_prime(rand());
    printf("Num p: %llu\n", p);
    uint64_t q = generate_prime(rand()+1000);
    printf("Num q: %llu\n", q);
    uint64_t n = p * q; // public

    uint64_t totient_func = lcm(p-1, q-1);
    printf("LCM: %lli\n", totient_func);
    uint64_t e = generate_e(totient_func); // public
    printf("Number e: %lli\n", e);
    uint64_t d = generate_d(e, totient_func); // private
    printf("Number d: %lli\n", d);
    uint64_t msg = 43621;
    printf("MSG: %lli\n", msg);
    uint64_t encrpt = encrypt_msg(n, e, msg);
    printf("Encrypted msg: %lli\n", encrpt);
    uint64_t dcrpt = decrypt_msg(encrpt, d, n);
    printf("Decrypted msg: %lli\n", dcrpt);


    printf("Number n: %lli\n", n);
    const uint64_t* result = find_p_and_q(n);
    for (int i = 0; i < 2; i++){
        printf("Numbers are: %lli\n", result[i]);
    }
    return 0;
}
