package list

import kotlin.concurrent.thread

fun main() {
    val list = LockFreeList<Int>()
    list.addFirst(0)
    val thread1 = thread(start = true) {
        for (i in 1..100) {
            val res1 = list.addAfter(i - 1, i)
            println("$res1 from 1")
        }
    }

    val thread2 = thread(start = true) {
        for (i in 101..200) {
            list.addFirst(i)
            println("added from 2")
        }
        list.remove(5)
        println("called remove from 2")
    }
    thread1.join()
    thread2.join()

    list.print()
}


// Output if it makes sense to add this
/*
true from 1
true from 1
true from 1
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
added from 2
added from 2
true from 1
called remove from 2
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
true from 1
List content:
200
199
198
197
196
195
194
193
192
191
190
189
188
187
186
185
184
183
182
181
180
179
178
177
176
175
174
173
172
171
170
169
168
167
166
165
164
163
162
161
160
159
158
157
156
155
154
153
152
151
150
149
148
147
146
145
144
143
142
141
140
139
138
137
136
135
134
133
132
131
130
129
128
127
126
125
124
123
122
121
120
119
118
117
116
115
114
113
112
111
110
109
108
107
106
105
104
103
102
101
0
1
2
3
4
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74
75
76
77
78
79
80
81
82
83
84
85
86
87
88
89
90
91
92
93
94
95
96
97
98
99
100

 */