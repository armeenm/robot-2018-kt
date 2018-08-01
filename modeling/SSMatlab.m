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

%C = [9.5493 0];
C = [1 0];

D = 0;

t = 0:0.01:2;
u = 1 * ones(size(t));
x0 = [0 0];

sys = ss(A, B, C, D);

sys.C = eye(2);

p1 = -10 + 5i;
p2 = -10 - 5i;

Kc = place(A, B, [p1 p2]);
%sys_cl = ss(A - B * Kc, B, C, D);
sys_cl = feedback(sys, Kc);

step(sys_cl);
%lsim(sys_cl, u, t, x0);
%xlabel('Time')
%ylabel('CIM (???)')
