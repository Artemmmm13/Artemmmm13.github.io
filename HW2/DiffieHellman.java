import java.util.Random;

public class DiffieHellman {
    private static final Random random = new Random();
    public static void main(String[] args) {
        System.out.println("======================================Diffie-Hellman key exchange======================================");
        long prime = generateRandomPrime(27);
        System.out.printf("Prime number(p): %d\n", prime);
        long generator = generatePrimitiveRoot(prime);
        System.out.printf("Generator number(g): %d\n", generator);
        long alicePrivateKey = random.nextLong(1, Long.MAX_VALUE);
        System.out.printf("Alice Private key: %d\n", alicePrivateKey);
        long bobPrivateKey = random.nextLong(1, Long.MAX_VALUE);
        System.out.printf("Bob Private Key: %d\n", bobPrivateKey);
        long alicePublicKey = modPow(generator, alicePrivateKey, prime);
        System.out.printf("Alice Public Key: %d\n", alicePublicKey);
        long bobPublicKey = modPow(generator, bobPrivateKey, prime);
        System.out.printf("Bob Public Key: %d\n", bobPublicKey);
        long aliceCommonSecret = modPow(bobPublicKey, alicePrivateKey, prime);
        long bobCommonSecret = modPow(alicePublicKey, bobPrivateKey, prime);
        System.out.printf("Alice Common Secret: %d\n", aliceCommonSecret);
        System.out.printf("Bob Common Secret: %d\n", bobCommonSecret);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Prime Factors Multiplication Result Factorization@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        long prime2 = generateRandomPrime(27);
        long multiplicationResult = prime*prime2;
        resultFactorization(multiplicationResult);
    }


    public static long generateRandomPrime(int bits){
        long candidatePrime;

        do{
            candidatePrime = random.nextLong((1L << (bits-1))+1, 1L << bits);
        } while (!isPrime(candidatePrime));

        return candidatePrime;
    }


    public static long generatePrimitiveRoot(long primeNumber) {
        long phi = primeNumber - 1;

        for (long j = 2; j <= primeNumber; j++) {
            if (isPrimitiveRoot(j, primeNumber, phi)) {
                return j;
            }
        }

        return -1; // No primitive root found
    }

    private static boolean isPrimitiveRoot(long g, long p, long phi) {
        if (gcd(g, p) != 1) {
            return false; // g and p are not coprime, so it can't be a primitive root.
        }

        long order = calculateOrder(g, p);
        return order == phi;
    }

    private static long calculateOrder(long g, long p) {
        long phi = p - 1;

        for (long d = 1; d <= phi; d++) {
            if (modPow(g, d, p) == 1) {
                return d;
            }
        }

        return phi;
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }


    public static void resultFactorization(long multiplicationResult){
        long prime1 = 2;
        while (true){
            if (multiplicationResult % prime1 == 0){
                long prime2 = multiplicationResult / prime1;
                if (isPrime(prime2)){
                    System.out.printf("Multiplication result(p1*p2): %d\n", multiplicationResult);
                    System.out.printf("Prime 1: %d\n", prime1);
                    System.out.printf("Prime 2: %d\n", prime2);
                    break;
                }
            }
            prime1++;
        }
    }



    private static long modPow(long base, long exponent, long modulus) {
        long result = 1;
        base = base % modulus;

        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }

        return Math.abs(result);
    }

    private static boolean isPrime(Long num) {
        if (num <= 1) {
            return false;
        }
        if (num <= 3) {
            return true;
        }
        if (num % 2 == 0 || num % 3 == 0) {
            return false;
        }
        for (int i = 5; (long) i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}
