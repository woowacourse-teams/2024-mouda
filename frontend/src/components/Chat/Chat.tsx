import {
  chatMessageStyle,
  messageContainer,
  senderStyle,
  timeStyle,
} from './Chatstyle';

import { Chat as ChatType } from '@_types/index';
import { PropsWithChildren } from 'react';
import UserPreview from '@_components/UserPreview/UserPreview';
import { formatHhmmToKoreanWithPrefix } from '@_utils/formatters';
import { useTheme } from '@emotion/react';

export interface ChatMessageProps extends PropsWithChildren {
  chat: ChatType;
}

export default function Chat(props: ChatMessageProps) {
  const { chat, children } = props;
  const { nickname, time, isMyMessage } = chat;
  const theme = useTheme();
  return (
    <div css={chatMessageStyle({ isMyMessage })}>
      {/* TODO: 추후 프로필 사진 추가시 변경해야함  */}
      <UserPreview imageUrl={''} />
      <div css={messageContainer({ isMyMessage })}>
        <span css={senderStyle({ theme })}>{nickname}</span>
        {children}
        <span css={timeStyle({ theme })}>
          {formatHhmmToKoreanWithPrefix(time)}
        </span>
      </div>
    </div>
  );
}
