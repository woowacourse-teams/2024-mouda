import { SerializedStyles, useTheme } from '@emotion/react';
import {
  container,
  messageContainer,
  peopleContainer,
  smallGrey400,
  tag,
  titleContainer,
  unreadContentCountContainer,
  unreadContentWrapper,
} from './ChattingPreview.style';

import ChatBubbleSvg from '@_components/Icons/ChatBubbleSvg';
import { HTMLAttributes } from 'react';
import POLICES from '@_constants/poclies';
import { Participant } from '@_types/index';
import UserPreviewList from '@_pages/Chatting/ChattingRoomPage/components/UserPreviewList/UserPreviewList';

interface ChattingPreviewProps extends HTMLAttributes<HTMLDivElement> {
  title: string;
  participants: Participant[];
  lastContent?: string;
  tagValue?: string;
  unreadCount?: number;
  themeColor?: string | SerializedStyles;
  fontColor?: string | SerializedStyles;
}

export default function ChattingPreview(props: ChattingPreviewProps) {
  const theme = useTheme();
  const {
    title,
    participants: profiles,
    lastContent,
    tagValue,
    themeColor = theme.colorPalette.yellow[100],
    fontColor = theme.colorPalette.white[100],
    onClick,
    unreadCount = 0,
  } = props;

  return (
    <div css={container({ theme, themeColor })} onClick={onClick}>
      <div css={messageContainer}>
        <div css={titleContainer}>
          <h2 css={theme.typography.s2}>{title}</h2>

          {tagValue && (
            <div css={tag({ theme, themeColor, fontColor })}>{tagValue}</div>
          )}
        </div>
        {lastContent && (
          <span css={[smallGrey400({ theme }), unreadContentWrapper]}>
            {lastContent}
          </span>
        )}
        {unreadCount > 0 && (
          <div css={unreadContentCountContainer}>
            <ChatBubbleSvg />
            <span css={theme.typography.Tiny}>
              {unreadCount > POLICES.maxUnreadMessageCount
                ? POLICES.maxUnreadMessageCount + '+'
                : unreadCount}
            </span>
          </div>
        )}
      </div>

      <div css={peopleContainer}>
        <span css={smallGrey400({ theme })}>{`${profiles.length}명`}</span>
        <UserPreviewList
          imageUrls={profiles.map((profile) => profile.profileUrl)}
        />
      </div>
    </div>
  );
}
