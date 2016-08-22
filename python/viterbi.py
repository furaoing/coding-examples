# -*- coding:utf-8 -*-

import copy
states = ('D', 'N', 'V')
obs = ('the', 'dog', 'barks')

start_prob = {'D': 0.1, 'N': 0.8, 'V': 0.1}

tran_prob = {'D': {'D': 0.1, 'N': 0.8, 'V': 0.1},
             'N': {'D': 0.1, 'N': 0.1, 'V': 0.8},
             'V': {'D': 0.1, 'N': 0.8, 'V': 0.1},
             }

emission_prob = {
    'D': {'the': 0.9, 'dog': 0.05, 'barks': 0.05},
    'N': {'the': 0.05, 'dog': 0.8, 'barks': 0.15},
    'V': {'the': 0.05, 'dog': 0.1, 'barks': 0.85}
}

phi = [{}]
# 路径概率表, 记录每个obs位置对应的index下的以X隐状态结尾的隐藏序列的最优概率
bp = [{}]
# 路径表, 记录phi表中每个最优概率对应的隐藏序列

length = len(obs)
for i in range(length):
    if i == 0:
        # 初始化
        for current_state in states:
            phi[0][current_state] = start_prob[current_state] * emission_prob[current_state][obs[0]]
            bp[0][current_state] = [current_state]
            # this is a sequence
    else:
        phi.append({})
        bp.append({})
        for current_state in states:
            probs = []
            hidden_sequence_candicates = []
            for previous_state in states:
                probs.append(phi[i-1][previous_state] *
                             tran_prob[previous_state][current_state] *
                             emission_prob[current_state][obs[i]])
                tmp = copy.deepcopy(bp[i-1][previous_state])
                tmp.append(current_state)
                hidden_sequence_candicates.append(tmp)

            max_prob = max(probs)
            max_prob_index = probs.index(max_prob)
            hidden_sequence = hidden_sequence_candicates[max_prob_index]

            phi[i][current_state] = max_prob
            bp[i][current_state] = hidden_sequence

print(phi[-1])
print(bp[-1])
