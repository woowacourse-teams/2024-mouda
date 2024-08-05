import ChattingPreview from '@_components/ChattingPreview/ChattingPreview';
import { MoimInfo } from '@_types/index';
import useChats from '@_hooks/queries/useChat';

interface ChattingPreviewWrapperProps {
  moim: MoimInfo;
  onClick: () => void;
}

export default function ChattingPreviewWrapper(
  props: ChattingPreviewWrapperProps,
) {
  const { moim, onClick } = props;
  const { chats, isLoading } = useChats(moim.moimId);
  return isLoading ? (
    <></>
  ) : (
    <ChattingPreview
      moim={moim}
      lastChat={chats?.at(-1)?.content}
      onClick={onClick}
    />
  );
}
