import CommentCard from '@_components/CommentCard/CommentCard';
import * as S from '@_components/CommentList/ComentList.style';
import { Comment } from '@_types/index';
import { HTMLProps } from 'react';

interface CommentListProps extends HTMLProps<HTMLDivElement> {
  comments: Comment[];
  onWriteClick: () => void;
}

export default function CommentList(props: CommentListProps) {
  const { comments, onWriteClick } = props;

  return (
    <div css={S.commentListBox()}>
      {comments.map((comment) => {
        return (
          <CommentCard
            key={comment.commentId}
            comment={comment}
            onWriteClick={onWriteClick}
          />
        );
      })}
    </div>
  );
}
