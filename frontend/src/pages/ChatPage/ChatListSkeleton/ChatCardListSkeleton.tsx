import ChatCardSkeleton from './ChatCardSkeleton/ChatCardSkeleton';
import * as S from './ChatCardListSkeleton.style';

export default function ChatCardListSkeleton() {
  return (
    <div css={S.cardListSection}>
      <ChatCardSkeleton />
      <ChatCardSkeleton />
      <ChatCardSkeleton />
      <ChatCardSkeleton />
      <ChatCardSkeleton />
      <ChatCardSkeleton />
      <ChatCardSkeleton />
      <ChatCardSkeleton />
    </div>
  );
}
