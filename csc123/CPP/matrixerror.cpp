/// Program adopted from https://cplusplus.com/forum/general/220937/

/*
"Hello, I was recently running a self-made programm in C++ and I got
the following message during execution in the command window:

terminate called after throwing an instance of 'std::bad_alloc'
what(): std::bad_alloc

Afterwards I receive a Windows message saying that the programm
stopped because I have not enough memory. Thing is I am using dynamic
memory for the program and I am afraid I have done something wrong
with it... Is there any change to fix this? thanks in advance "
*/
/*  
On linux, the program crashed:
"
Welcome to the C++ program for Molecular Dynamics simulation. Introduce how many particles you want to work with: 10000
Now choose the value of the parameter a>0 , associated to the temperature: 2.5

Include now the total number of time steps you want to take for the simulation: 100
The initial velocities have been allocated in a matrix. 

End of the program 
double free or corruption (!prev)
Aborted (core dumped)
"


           ============= YOUR ASSIGNMENT: ==============

1. Identify the cause(s) of the memory error (place your answer in comments)
2. Rewrite the program in modern C++ ("clean c++").

Instead of double**, use the following types for dynamically allocated
2D array:

#include<memory>
...

typedef std::unique_ptr<double[]> double1d;    // replaces double*
typedef std::unique_ptr<double1d[]> double2d;  // replaces double**

Follow the "RULE OF ZERO".  Remove all uses of raw pointers and replace them
with smart pointers and references.  You must be careful to use a reference
(matrix& or matrix&&) where appropriate.  The resulting program cannot be
less efficient than the original program was intended to be.

This is the length of a "real world" program, though it's actually not
that long.  Most of what you need to replace is relatively straightforward.
But be careful.  For example, consider the void populate(double** m)
function.  Do not just change this to void populate(double2d m): because
double2d is a unique_ptr, which means there must be a transfer of ownership
(move) to the local m when the function is called. This means that whatever 
double2d you passed to it would be destroyed after the call!  Instead, 
change the signature of this function to void populate(double2d& m); 
Mixing smart pointers with references is OK.

Also uncomment the call to operator_testing() inside main when you've fixed
the program.
*/

#include <iostream>
#include <cmath>
#include <stdlib.h>
#include <time.h>
#include <fstream>
#define pi 3.14159265359897
using namespace std;

///// header for matrix
struct matrix {
private:
  double** the_matrix;
  int x;  // rows
  int y;  // columns
public:  
  matrix();
  matrix(int rows,int cols);
  ~matrix();
  double** alloc(int rows,int cols);
  void clean();
  void printm();
  double** getmatrix();
  void trans();
  int nrows();
  int ncolumns();
  double get(int row,int column);
  void fill(double value,int row,int column);
  void DOS_populate();
  void flip(matrix& m,int row,int column);
  void populate(double** pp);
  void eye();
  void zeros();
  double trace();
  void add(matrix& m);
  void subs(matrix& m);
  void multiply(matrix& m);
  void double_rand(double min,double max);
}; // matrix header, implementations below

//allocation of memory 
double** matrix::alloc(int rows,int cols){
	x=rows;
	y=cols;	
	
	double** new_matrix=new double*[x];
	for(int i=0;i<x;i++){
		new_matrix[i]=new double[y];
	}
	return new_matrix;
	
}
/* I'LL DO THIS ONE FOR YOU:
double2d matrix::alloc(int rows,int cols){
	x=rows;
	y=cols;	
	double2d new_matrix= make_unique<double1d[]>(x);
	for(int i=0;i<x;i++){
	  new_matrix[i]= make_unique<double[]>(y);
	}
	return new_matrix;
}
*/

//Cleaning memory /deallocate
matrix::~matrix(){
	
	for(int i=0;i<x;i++){
		delete[] the_matrix[i];
	}
	delete[] the_matrix;
}
//clean individual matrix 
void matrix::clean(){
	
	for(int i=0;i<x;i++){
		delete[] the_matrix[i];
	}
	delete[] the_matrix;
	
}

//CONSTRUCTORS
//allocate memory
matrix::matrix(){
	
	the_matrix=alloc(0,0);
	
};
//constructor of the matrix as object
matrix::matrix(int rows,int cols){
	
	the_matrix=alloc(rows,cols);
	
}

/// FUNCTIONS
//Print matrix in the command window
void matrix::printm(){
	
	for(int i=0;i<x;i++){
		for(int j=0;j<y;j++){
			
			cout<<the_matrix[i][j]<<"   ";
		}
		cout<<endl;
		
	}
	cout<<endl<<endl;
	
}

//get matrix elements as double pointer adress

double** matrix::getmatrix(){
	
	return the_matrix;
}
//transpose matrix
void matrix::trans(){
	
	double** transpose=new double*[y];
	for(int i=0;i<y;i++){
		transpose[i]=new double[x];
	}
		
	for(int i=0;i<y;i++){
		for(int j=0;j<x;j++){
			
			transpose[i][j]=the_matrix[j][i];
		}
		
	}
	
	for(int i=0;i<x;i++){
		delete[] the_matrix[i];
	}
	delete[] the_matrix;
	
	the_matrix=transpose;
	
	int new_cols;
	new_cols=x;
	x=y;
	y=new_cols;
			
}

//Number of rows: return
int matrix::nrows(){
	
	return x;
}

//number of columns return:
int matrix::ncolumns(){
	
	return y;
}
//Get element (i,j)
double matrix::get(int row,int column){
	
	return the_matrix[row][column];
	
}

//POinter to element (i,j) of the matrix
void matrix::fill(double value,int row,int column){

the_matrix[row][column]=value;

}

//Populate matrix by elements from command window
void matrix::DOS_populate(){
	
	for(int i=0;i<x;i++){
		for(int j=0;j<y;j++){
			
			cout<<"Enter elements from left to right :";
			cin>>the_matrix[i][j];
		}
	}
	
}

//flips values of a whole sector of the matrix
void matrix::flip(matrix& m,int row,int column){
	int frow;//final row
	int fcolumn;//final colum;
	
	frow=m.nrows();
	fcolumn=m.ncolumns();
	
	if ((fcolumn>y)||(frow>x)){
	
	cout<<"Error: the matrix you are trying to insert is too big "<<endl;
	exit(EXIT_FAILURE);
	}
	
	
	for(int i=row;i<frow;i++){
		for(int j=column;j<fcolumn;j++){
			
			the_matrix[i][j]=m.get(i,j);
			
		}
	}
	
	
}

//Populate from external array
void matrix::populate(double** pp){
	
	
	
	
}

//Identity matrix
void matrix::eye(){
	
	
	for(int i=0;i<x;i++){
		for(int j=0;j<y;j++){
			
			the_matrix[i][j]=0;
			if(i==j){
				the_matrix[i][j]=1;
			}
		}
		
	}
	
		
}

//zeros matrix
void matrix::zeros(){
	
	
	for(int i=0;i<x;i++){
		for(int j=0;j<y;j++){
			
			the_matrix[i][j]=0;
			
		}
		
	}
	

}

//Trace of matrix

double matrix::trace(){
    
    double tr;
    tr=0.0;
	for(int i=0;i<x;i++){
		for(int j=0;j<y;j++){
			tr=tr + the_matrix[i][j];
		}
		
	}
	return tr;
}
//Sum of matrices
void matrix::add(matrix& m){
	
	if(x!=m.nrows() || y!=m.ncolumns()){
		
		cout<<"Matrix dimensions must agree"<<endl;
		exit(EXIT_FAILURE);
	}
	for(int i=0;i<x;i++){
		for(int j=0;j<y;j++){
			
			the_matrix[i][j]+=m.get(i,j);
			
		}
	}
	
}
//substraction of matrices
void matrix::subs(matrix& m){
	
	for(int i=0;i<x;i++){
		for(int j=0;j<y;j++){
			
			the_matrix[i][j]-=m.get(i,j);
			
		}
	}
	
}

void matrix::multiply(matrix& m){
	
	
	double** producto=new double*[x];
	
	for(int i=0;i<x;i++){
		producto[i]=new double[m.ncolumns()];
	}
	
		
	if(y!=m.nrows()){
		cout<<" Matrix multiplication fails"<<endl;
	}
	double suma;
	
	for(int i=0;i<x;i++){
		for(int j=0;j<m.ncolumns();j++){
			
			suma=0.0;
			for(int k=0;k<m.nrows();k++){
				
				suma=suma+the_matrix[i][k]*(m.get(k,j));
				
			}
			
			producto[i][j]=suma;
			
		}
		
	}
	
	for(int i=0;i<x;i++){
		delete[] the_matrix[i];
	}
	delete[] the_matrix;
	
	the_matrix=producto;
	y=m.ncolumns();
	
	
}

//Random matrix generator in the interval min - max (DOUBLE)

void matrix::double_rand(double min,double max){
	
	srand(time(NULL));
	double interval,bb;
	double num;//the random number generated	
	
	interval=abs((max-min));
	
	for(int i=0;i<x;i++){
		for(int j=0;j<y;j++){
			
			bb=(rand())/(1.0*RAND_MAX);
			the_matrix[i][j]=interval*bb + min;
		}
		
	}
			
}

//////////////////////
//OPERATORS OVERLOAD FOR MATRICES
//ADITION
matrix operator+(matrix& M1,matrix& M2){
	int n1,m1,n2,m2;
	double p;//value to fill the matrix
	
	n1=M1.nrows();
	m1=M1.ncolumns();
	n2=M2.nrows();
	m2=M2.ncolumns();
	
	matrix result(n1,m1);
	
	for(int i=0;i<n1;i++){
		for(int j=0;j<m1;j++){
			
			p=M1.get(i,j)+M2.get(i,j);
			result.fill(p,i,j);
			
		}
	}
		
	return result;
		
}
//SUBSTRACTION
matrix operator-(matrix& M1,matrix& M2){
	int n1,m1,n2,m2;
	double p;//value to fill the matrix
	
	n1=M1.nrows();
	m1=M1.ncolumns();
	n2=M2.nrows();
	m2=M2.ncolumns();
	
		
	matrix result(n1,m1);
	
	for(int i=0;i<n1;i++){
		for(int j=0;j<m1;j++){
			
			p=M1.get(i,j)-M2.get(i,j);
			result.fill(p,i,j);
			
		}
	}
		
	return result;
		
}

//MATRIX MULTIPLICATION
matrix operator*(matrix& M1,matrix& M2){
	int n1,m1,n2,m2;
	
	n1=M1.nrows();
	m1=M1.ncolumns();
	n2=M2.nrows();
	m2=M2.ncolumns();
	matrix result(n1,m2);
	double pp;
	
	for(int i=0;i<n1;i++){
		
		for(int j=0;j<m2;j++){
         
		 pp=0.0;
		 for(int k=0;k<m1;k++){
		 	pp=pp+ M1.get(i,k)*M2.get(k,j);
		 }			
		  
		  result.fill(pp,i,j);	
		}
		
	}	
	return result;
	
}

void operator_testing(){
	
	srand(time(NULL));	
	matrix A(2,2);
	matrix B(2,2),C;
   
    A.double_rand(0,1);
    
    B.double_rand(0,1);
    A.printm();
    cout<<endl<<endl;
    B.printm();
    
    C=A*B;
     
     C.printm();
   	// clean matrices objects;
	A.clean();
	B.clean();
	C.clean();
}


///////////////////////// main
/////////////////////////////////////////////////////////////////


double* v_rand_MB(int npart, double a){ //arguments are # of particles and parameter "a", related to temperature of the gas
 int count;//counts the total number of accepted random numbers from the distribution
 count=0;
//Pointer to the array of velocities
 double* myp;
 double interval,bb,vmax,Pd,Ptest,num,den,alp;
 
 vmax=5.0*a;
 
 interval=abs(vmax);
 
 
 myp=new double[npart];//The pointer points towards an npart dimensional array of type double
  
  //loop for generation of random numbers
  while(count<npart){
  	 
  	 //Generate a random number on the desired interval.
  	 bb=rand()/(1.0*RAND_MAX);
  	 bb=interval*bb;
    	 
  	 //Probability density, i.e., MB distribution
  	 den=sqrt(2.0/pi)/(1.0*pow(a,3));
  	 alp=1.0/(2*pow(a,2));
  	 num=pow(bb,2)*(exp(-alp*pow(bb,2)));
  	   	
  	 
  	 Pd=den*num; //probability associated to the random number bb
  	 Ptest=rand()/(1.0*RAND_MAX);
  	   	 
  	 if(Ptest<Pd){
  	 	
	   myp[count]=bb;
	   count=count+1;   
  	 	
  	 }
  	 
  	   	
  }
  
  

return myp;
}
//function that fills the initial velocities matrix

void initiate_velocities(matrix& m, double* myp){
	
	//generate random numbers for theta and phi	
	double theta,phi,px,py,pz;	
	int dimr; //dimensions of rows and columns of matrix
	dimr=m.nrows();
	
	for(int i=0;i<dimr;i++){
		//generate random angles
		theta=(rand()/(1.0*RAND_MAX))*pi;
	    phi=(rand()/(1.0*RAND_MAX))*(2*pi); 
	    
	    //variables to fill the matrix
	    px=myp[i]*(sin(theta)*cos(phi));
	    py=myp[i]*(sin(theta)*sin(phi));
	    pz=myp[i]*cos(theta); 
		//components one by one, filled with the appropiate value
		//x component
		m.fill(px,i,0);
		//y component
		m.fill(py,i,1);	  
		//z component
		m.fill(pz,i,2); 
		
	}
	
	//ERASE THIS LINE WHEN PROGRAM IS COMPLETED
	//m.printm();
	
}

//generate position of particles
void initiate_positions(matrix& m,int Npart){
	int count,ylim,zlim;
	double density,epsilon,dx,x,y,z;//density and minimum radius associated to each particle, and epsilon is the value in order to avoid one particle lying on one of
	//the faces of the cube;
	epsilon=0.1;
	//The box lives in the interval -1/2 + 1/2 for each coordinate.
	density=1.0/(Npart);
    //We need to generate NxNxN positions (a cube). We start by creating the points in the z=+epsilon level, and we rise them till z= 1-epsilon. 
    dx=0.8/(49.0); //element separating two points in the initial positions cube
    
    matrix rowpos(50,1);
    x=0.0;
    
    for(int i=0;i<50;i++){
    	rowpos.fill(x,i,0);
    	x=x+dx;
    }
    
    z=0;//Initialize at z=0 plane
    count=0;
    y=0;
    ylim=49;
    zlim=2499;
    
	for(int k=0;k<Npart;k++){
		
		m.fill(rowpos.get(count,0),k,0);
	    m.fill(y,k,1);//fill y  and z values
	    m.fill(z,k,2);
	    
	    if(k==ylim){
	    	ylim=ylim+50;
	    	y=y+dx;	    	
	    	
	    }
		if(k==zlim){
			zlim=zlim+2500;
			y=0.0;
			z=z+dx;
		}
		count=count+1;
		if(count==50){
			count=0;
		}
		
		
	}    
	
}
//store initial data in .txt file

void store1(matrix& m1,matrix& m2,int Npart){
	
    ofstream dataR("pos_data.txt");//store positions in txt file
    ofstream dataV("vel_data.txt");//store velocities
	
	for(int i=0;i<Npart;i++){
		
	dataR<<m1.get(i,0)<<"  "<<m1.get(i,1)<<"  "<<m1.get(i,2);
	dataR<<endl;
	
	dataV<<m2.get(i,0)<<"  "<<m2.get(i,1)<<"  "<<m2.get(i,2);
	dataV<<endl;
	
	}
	
	
}

//function that gets the matrix of relative distances between particles, therefore giving the forces between each of them
void forces_matrix(matrix& r0,matrix& forces,double gamma,double beta,int Npart){
	
	double modulus,F;//modulus and force
	
	for(int i=0;i<Npart;i++){
		for(int j=0;j<Npart;j++){
			if(i==j){
				
				forces.fill(1000000,i,j); //a particle doesnt interact with itself, so the force with this choice will be zero
			}
			
			modulus=sqrt( pow(r0.get(i,0)-r0.get(j,0),2) +  pow(r0.get(i,1)-r0.get(j,1),2) +pow(r0.get(i,2)-r0.get(j,2),2));
			
					
		}
		
	}
}

/////////////// main
int main(){
  //operator_testing(); // UNCOMMENT AFTER FIXING PROGRAM
	
	srand(time(NULL));//Initialize seed for random numbers
	//Definition of variables/objects    
	
	int Npart,npt,Nmax; //Total number of particles, total number of time frames, maximum number of particles allowed
	double tf,dt,a,gamma,beta;//Final time, differential of time, temp.parameter MB distribution. The parameters gamma and r0 belong to the
	//Buckinham potential, and must be chosen carefully. 
	gamma=0.2;
	beta=0.02;
	
	double* vpoint;//Pointer to velocities distribution
    
	cout<<"Welcome to the C++ program for Molecular Dynamics simulation. Introduce how many particles you want to work with: ";
	cin>>Npart;
	Nmax=125000; //The maximum allowed size of the cube is 50x50x50
	while(Npart>Nmax){
		cout<<"Sorry, number of particles is too big, please reduce it ";
		cin>>Npart;
	}
	
	cout<<"Now choose the value of the parameter a>0 , associated to the temperature: ";
	cin>>a;
	
	while(a<0){
		
		cout<<"Sorry, but the parameter 'a' needs to be positive, try again: ";
		cin>>a;
	}
	
	cout<<endl<<"Include now the total number of time steps you want to take for the simulation: ";
	cin>>npt;
	
	//The initial positions and velocities of the particles. Lets use a Maxwell-Boltzmann distribution for the velocities, whereas the position of particles 
	//should be occupying vertices of a cube in space.
	//Matrices of initial positions and positions at time t, and for velocities., and the matrix of relative distances between particles, relevant for the 
	//forces acting over each individual particle
	
	matrix r0(Npart,3),rt(Npart,3),v0(Npart,3),vt(Npart,3); //,forces(Npart,Npart);	
	//Generate the array of random velocities
	vpoint=v_rand_MB(Npart,a);
	//Generate matrix of random velocities components
	initiate_velocities(v0,vpoint);
	cout<<"The initial velocities have been allocated in a matrix. "<<endl<<endl;
	initiate_positions(r0,Npart);
	
	store1(r0,v0,Npart);
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	r0.clean();
	v0.clean();
	rt.clean();
	vt.clean();
	//forces.clean();
		
	delete[] vpoint;//Free memory block
	
	
	cout<<"End of the program "<<endl;
	
	return 0;
}
