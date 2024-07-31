import * as S from '@_components/CommentCard/CommentCard.style';
import { Comment } from '@_types/index';
import { useTheme } from '@emotion/react';

import { HTMLProps } from 'react';

export interface CommentCardProps extends HTMLProps<HTMLDivElement> {
  comment: Comment;
}

export default function CommentCard(props: CommentCardProps) {
  const theme = useTheme();
  const { comment } = props;

  return (
    <div css={S.commentContainer()}>
      <div css={S.profileImage()}></div>
      <div css={S.commnetBox()}>
        <div css={S.commnetHeader}>
          <div css={S.commentHeaderLeft}>
            <div css={theme.typography.small}>{comment.nickname}</div>
            <div css={S.timestamp({ theme })}>{comment.dateTime}</div>
          </div>
          <div css={S.commentHeaderRight({ theme })}>
            <button>수정</button>
            <button>삭제</button>
            <button>답글쓰기</button>
          </div>
        </div>
        <div>{comment.content}</div>
      </div>
    </div>
  );
}
