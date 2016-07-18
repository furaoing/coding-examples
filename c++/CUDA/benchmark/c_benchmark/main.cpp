#include <stdio.h>
#include <time.h>
#include <stdlib.h>

void saxpy(int i, float *x, float *y, float *z)
{
    for(int k = 0; k < 1000; ++k) {
      z[i] = x[i] + y[i];
      z[i] += 1;
    }
}

int main(void)
{
    clock_t start_time = clock();
    int N = 1<<20;
    int sum = 0;
    float *x, *y, *z, *d_x, *d_y, *d_z;
    x = (float*)malloc(N*sizeof(float));
    y = (float*)malloc(N*sizeof(float));
    z = (float*)malloc(N*sizeof(float));



    for (int i = 0; i < N; ++i) {
    x[i] = 1.0f;
    y[i] = 2.0f;
    }

    for (int k = 0; k < N; ++k) {
    saxpy(k, x, y, z);
    }

    clock_t end_time = clock();
    float time_c = 1000*(((float)(end_time - start_time))/CLOCKS_PER_SEC);
    // time_c in milliseconds
    printf("Time consumed: %f millsec \n", time_c);
    for (int i = 0; i < N; ++i) {
    sum += z[i];
    }
    printf("Sum: %d \n", sum);

    free(x);
    free(y);
    free(z);
}
