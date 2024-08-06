import {
  chatMessageStyle,
  messageContainer,
  messageStyle,
  senderStyle,
  timeStyle,
} from './Chatstyle';

import type { Chat } from '@_types/index';
import UserPreview from '@_components/UserPreview/UserPreview';
import { formatHhmmToKoreanWithPrefix } from '@_utils/formatters';
import { useTheme } from '@emotion/react';

export interface ChatMessageProps {
  chat: Chat;
}

export default function Chat(props: ChatMessageProps) {
  const { chat } = props;
  const { content, nickname, time, isMyMessage } = chat;
  const theme = useTheme();
  return (
    <div css={chatMessageStyle({ isMyMessage })}>
      {/* TODO: 추후 프로필 사진 추가시 변경해야함  */}
      <UserPreview imageUrl={''} />
      <div css={messageContainer({ isMyMessage })}>
        <span css={senderStyle({ theme })}>{nickname}</span>
        <div css={messageStyle({ theme, isMyMessage })}>{content}</div>
        <span css={timeStyle({ theme })}>
          {formatHhmmToKoreanWithPrefix(time)}
        </span>
      </div>
    </div>
  );
}
