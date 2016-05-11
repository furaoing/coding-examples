#include <stdio.h>
#include <time.h>
#include <stdlib.h>

#define THREAD_PER_BLOCK 256

__global__ void saxpy(int N, float *x, float *y, float *z)
{
  int i = threadIdx.x + blockIdx.x * blockDim.x;
  if (i < N) {
    for(int k = 0; k < 1000; ++k) {
      z[i] = x[i] + y[i];
      z[i] += 1;
    }
  }
}

int main(void)
{
  clock_t start_time = clock();
  int N = 1<<20;
  float *x, *y, *z, *d_x, *d_y, *d_z;
  int sum = 0;
  x = (float*)malloc(N*sizeof(float));
  y = (float*)malloc(N*sizeof(float));
  z = (float*)malloc(N*sizeof(float));

  cudaMalloc(&d_x, N*sizeof(float));
  cudaMalloc(&d_y, N*sizeof(float));
  cudaMalloc(&d_z, N*sizeof(float));

  for (int i = 0; i < N; i++) {
    x[i] = 1.0f;
    y[i] = 2.0f;
  }

  cudaMemcpy(d_x, x, N*sizeof(float), cudaMemcpyHostToDevice);
  cudaMemcpy(d_y, y, N*sizeof(float), cudaMemcpyHostToDevice);

  // Perform SAXPY on 1M elements
  saxpy<<<(N+THREAD_PER_BLOCK-1)/THREAD_PER_BLOCK,THREAD_PER_BLOCK>>>(N, d_x, d_y, d_z);

  cudaMemcpy(z, d_z, N*sizeof(float), cudaMemcpyDeviceToHost);
  clock_t end_time = clock();
  float time_c = 1000*(((float)(end_time - start_time))/CLOCKS_PER_SEC);
  // time_c in milliseconds
  printf("Time consumed: %f millsec \n", time_c);

  for (int i = 0; i < N; ++i) {
    sum += z[i];
  }
  printf("Sum: %d \n", sum);

  cudaFree(d_x);
  cudaFree(d_y);
  cudaFree(d_z);
  free(x);
  free(y);
  free(z);
}
