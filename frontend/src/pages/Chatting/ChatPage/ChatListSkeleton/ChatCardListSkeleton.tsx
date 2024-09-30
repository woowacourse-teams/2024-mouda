import ListContent from '@_layouts/components/ListContent/ListContent';
import ChatCardSkeleton from './ChatCardSkeleton/ChatCardSkeleton';

export default function ChatCardListSkeleton() {
  return (
    <ListContent>
      <ChatCardSkeleton />
      <ChatCardSkeleton />
      <ChatCardSkeleton />
      <ChatCardSkeleton />
      <ChatCardSkeleton />
      <ChatCardSkeleton />
      <ChatCardSkeleton />
      <ChatCardSkeleton />
    </ListContent>
  );
}
