%% CIM Model %%

% Constants %
J = 7.7500e-05;
b = 8.9100e-05;
Kt = 0.0184;
Ke = 0.0211;
R = 0.0916;
L = 5.9000e-05;

% Matrices %
A = [-b/J  Kt/J
     -Ke/L -R/L];

B = [0
     1/L];

C = [9.5493 0];

D = 0;

% Constants %
real = 0.0482;
imag = 0.0002;
dt = 0.005;
tlen = 1;

sys = ss(A, B, C, D);

%{
if real ^ 2 + imag ^ 2 > 1
	fprintf("Outside of unit circle!\n");
	return;
end

p1 = real + imag * 1i;
p2 = real - imag * 1i;
Kc = place(sys.A, sys.B, [p1 p2]);

sys_cl = ss(A - B * Kc, B * Kc, C - D * Kc, D * Kc);

step(sys_cl)
%}

dsys = c2d(sys, dt);

if real ^ 2 + imag ^ 2 > 1
	fprintf("Outside of unit circle!\n");
	return;
end

p1 = real + imag * 1i;
p2 = real - imag * 1i;
Q = C'*C;
R = 1;
Kc = lqr(dsys.A, dsys.B, Q, R)
%Kc = place(dsys.A, dsys.B, [p1 p2]);

r = [555
     0]; 
     
x = [0
     0];

%{
NxNu = [A B; C D] \ [zeros(size(A, 1), 1); ones(size(D, 1), 1)];
Nx = NxNu(1:size(A, 1));
Nu = NxNu(end);
Kff = Nu / Nx;%[Nu, 0];
%}
u = Kc * (r - x);% + Kff * r;
%u = 12;

t = [0:dt:tlen];

x_sim_vel = zeros(size(t));
x_sim_cur = zeros(size(t));

for n = 1 : length(t)
	x = dsys.A * x + dsys.B * u;
	u = Kc * (r - x);% + Kff * r;

	x_sim_vel(n) = x(1, 1);
	x_sim_cur(n) = x(2, 1);
end

figure

subplot(2, 1, 1);
plot(t, x_sim_vel);
title("Velocity");

subplot(2, 1, 2);
plot(t, x_sim_cur);
title("Current");
