#include <stdio.h>
#include <time.h>
#include <stdlib.h>

#define THREAD_PER_BLOCK 512

__global__ void saxpy(int N, double *x, double *y, double *z)
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
  double *x, *y, *z, *d_x, *d_y, *d_z;
  int sum = 0;



clock_t cpumemalloc_start = clock();
  x = (double*)malloc(N*sizeof(double));
  y = (double*)malloc(N*sizeof(double));
  z = (double*)malloc(N*sizeof(double));
clock_t cpumemalloc_end = clock();
 float time_cpumemalloc = 1000*(((double)(cpumemalloc_end - cpumemalloc_start))/CLOCKS_PER_SEC);
printf("Time consumed cpu mem alloc: %f millsec \n", time_cpumemalloc);


clock_t deviceset_start = clock();
  cudaSetDevice(0);
clock_t deviceset_end = clock();
 float time_deviceset = 1000*(((double)(deviceset_end - deviceset_start))/CLOCKS_PER_SEC);
printf("Time consumed deviceset alloc: %f millsec \n", time_deviceset);


 clock_t gpumemallo_start = clock();
  cudaMalloc(&d_x, N*sizeof(double));
  cudaMalloc(&d_y, N*sizeof(double));
  cudaMalloc(&d_z, N*sizeof(double));
clock_t gpumemallo_end = clock();
float time_gpumemallo = 1000*(((double)(gpumemallo_end - gpumemallo_start))/CLOCKS_PER_SEC);
printf("Time consumed gpu mem alloc: %f millsec \n", time_gpumemallo);


clock_t value_assign_start = clock();
  for (int i = 0; i < N; i++) {
    x[i] = 1.0;
    y[i] = 2.0;
  }
clock_t value_assign_end= clock();
float time_value_assign = 1000*(((double)(value_assign_end - value_assign_start))/CLOCKS_PER_SEC);
printf("Time consumed value_assign alloc: %f millsec \n", time_value_assign);


clock_t gpumemcpy_start = clock();
  cudaMemcpy(d_x, x, N*sizeof(double), cudaMemcpyHostToDevice);
  cudaMemcpy(d_y, y, N*sizeof(double), cudaMemcpyHostToDevice);
clock_t gpumemcpy_end = clock();
 float time_gpucpy = 1000*(((double)(gpumemcpy_end - gpumemcpy_start))/CLOCKS_PER_SEC);
printf("Time consumed gpu memcpy: %f millsec \n", time_gpucpy);




 clock_t gpucom_start = clock();
  // Perform SAXPY on 1M elements
  saxpy<<<(N+THREAD_PER_BLOCK-1)/THREAD_PER_BLOCK,THREAD_PER_BLOCK>>>(N, d_x, d_y, d_z);
clock_t gpucom_end = clock();
float time_gpucom = 1000*(((double)(gpucom_end - gpucom_start))/CLOCKS_PER_SEC);
printf("Time consumed gpu compute: %f millsec \n", time_gpucom);
  


clock_t gpumemcpyback_start = clock();
cudaMemcpy(z, d_z, N*sizeof(double), cudaMemcpyDeviceToHost);
clock_t gpumemcpyback_end = clock();
float time_gpumemcpyback = 1000*(((double)(gpumemcpyback_end - gpumemcpyback_start))/CLOCKS_PER_SEC);
printf("Time consumed gpu memcpy back: %f millsec \n", time_gpumemcpyback);


clock_t valuesum_start = clock();
  for (int i = 0; i < N; ++i) {
    sum += z[i];
  }
  printf("Sum: %d \n", sum);
clock_t valuesum_end = clock();
 float time_valuesum = 1000*(((double)(gpumemcpy_end - gpumemcpy_start))/CLOCKS_PER_SEC);
printf("Time consumed gpu valuesum: %f millsec \n", time_valuesum);


clock_t gpumemfree_start = clock();
  cudaFree(d_x);
  cudaFree(d_y);
  cudaFree(d_z);
clock_t gpumemfree_end = clock();
float time_gpumemfree = 1000*(((double)(gpumemfree_end - gpumemfree_start))/CLOCKS_PER_SEC);
printf("Time consumed gpu mem free: %f millsec \n", time_gpumemfree);


clock_t memfree_start = clock();
  free(x);
  free(y);
  free(z);
clock_t memfree_end = clock();
float time_memfree = 1000*(((double)(memfree_end - memfree_start))/CLOCKS_PER_SEC);
printf("Time consumed cpu mem free: %f millsec \n", time_memfree);


clock_t end_time = clock();
  float time_c = 1000*(((double)(end_time - start_time))/CLOCKS_PER_SEC);
  // time_c in milliseconds
 printf("Time consumed: %f millsec \n", time_c);
}


