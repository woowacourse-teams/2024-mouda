import * as S from '@_components/CommentCard/CommentCard.style';
import ProfileFrame from '@_components/Profile/ProfileFrame';
import { Comment } from '@_types/index';
import { useTheme } from '@emotion/react';

import { HTMLProps } from 'react';

export interface CommentCardProps extends HTMLProps<HTMLDivElement> {
  comment: Comment;

  onWriteClick?: () => void;
}

export default function CommentCard(props: CommentCardProps) {
  const theme = useTheme();
  const { comment, onWriteClick } = props;

  return (
    <div css={S.commentContainer()}>
      <div css={S.commentWrapper()}>
        <ProfileFrame width={3} height={3} src={comment.src}></ProfileFrame>
        <div css={S.commnetBox()}>
          <div css={S.commnetHeader}>
            <div css={S.commentHeaderLeft}>
              <div css={theme.typography.small}>{comment.nickname}</div>
              <div css={S.timestamp({ theme })}>{comment.dateTime}</div>
            </div>
            <div css={S.commentHeaderRight({ theme })}>
              <button>수정</button>
              <button>삭제</button>
              <button onClick={onWriteClick}>답글쓰기</button>
            </div>
          </div>
          <div>{comment.content}</div>
        </div>
      </div>
      {comment.child && comment.child.length > 0 && (
        <div css={S.commentChildBox()}>
          {comment.child.map((childComment) => (
            <CommentCard key={childComment.id} comment={childComment} />
          ))}
        </div>
      )}
    </div>
  );
}
