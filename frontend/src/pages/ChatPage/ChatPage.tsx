import ChattingPreviewLayout from '@_layouts/ChattingPreviewLayout/ChattingPreviewLayout';
import ChattingPreviewWrapper from '@_components/ChattingPreviewWrapper/ChattingPreviewWrapper';
import useMyMoims from '@_hooks/queries/useMyMoim';
import { useNavigate } from 'react-router-dom';

import { useTheme } from '@emotion/react';
import { Fragment } from 'react';

export default function ChatPage() {
  const theme = useTheme();
  const { moims, isLoading } = useMyMoims();
  const navigate = useNavigate();
  return (
    <ChattingPreviewLayout>
      <ChattingPreviewLayout.Header>
        <ChattingPreviewLayout.Header.Left>
          <h2 css={theme.typography.h5}>채팅</h2>
        </ChattingPreviewLayout.Header.Left>
      </ChattingPreviewLayout.Header>
      <ChattingPreviewLayout.ContentContainer>
        {!isLoading &&
          moims?.map((moim, id) => (
            <ChattingPreviewWrapper
              moim={moim}
              key={id}
              onClick={() => navigate(`/chatting-room/${moim.moimId}`)}
            />
          ))}
      </ChattingPreviewLayout.ContentContainer>
    </ChattingPreviewLayout>

  );
}
