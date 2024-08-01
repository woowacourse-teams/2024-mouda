import {
  container,
  peopleContainer,
  smallGrey400,
} from './ChattingPreview.style';

import UserPreviewList from '@_components/UserPreviewList/UserPreviewList';
import { useTheme } from '@emotion/react';

interface ChattingPreviewProps {
  title: string;
  participants: { imageUrl: string }[];
  lastChat?: string;
}

export default function ChattingPreview(props: ChattingPreviewProps) {
  const { title, lastChat, participants } = props;
  const theme = useTheme();

  return (
    <div css={container}>
      <div>
        <h2 css={theme.typography.s2}>{title}</h2>
        {lastChat && <span css={smallGrey400({ theme })}>{lastChat}</span>}
      </div>

      <div css={peopleContainer}>
        <span css={smallGrey400({ theme })}>{`${participants.length}명`}</span>
        <UserPreviewList
          imageUrls={participants.map((person) => person.imageUrl)}
        />
      </div>
    </div>
  );
}
