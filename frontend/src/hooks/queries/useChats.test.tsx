import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactNode, act } from 'react';
import {
  chatSliceIndexes,
  initChatIndex,
  nowChatServerData,
  pushNextChatsIntoSever,
} from '../../mocks/handler/chatHandler';

import { renderHook } from '@testing-library/react';
import useChats from './useChats';

const queryClient = new QueryClient();
const wrapper = ({ children }: { children: ReactNode }) => (
  <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
);

describe('useChats', () => {
  afterEach(() => {
    nowChatServerData.length = 0;
    initChatIndex();
  });
  it('초기의 chats은 빈 배열이다', () => {
    const { result } = renderHook(() => useChats(1), { wrapper });

    expect(result.current.chats).toHaveLength(0);
  });

  it('chats의 값은 서버의 값과 같다', async () => {
    const { result, rerender } = renderHook(() => useChats(1), { wrapper });
    for (let i = 0; i < chatSliceIndexes.length; i++) {
      await act(async () => {
        pushNextChatsIntoSever();
        await new Promise((res) => setInterval(res, 100));
        rerender();
      });
      expect(result.current.chats).toEqual(nowChatServerData);
    }
  });
});
