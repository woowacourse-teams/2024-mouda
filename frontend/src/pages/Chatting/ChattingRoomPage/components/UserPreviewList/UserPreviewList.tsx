import Meatball from '@_common/assets/meatball.svg';
import UserPreview from '@_components/UserPreview/UserPreview';
import { list } from './UserPreviewList.style';

interface UserPreviewListProps {
  imageUrls: string[];
  size?: string;
}

export default function UserPreviewList(props: UserPreviewListProps) {
  const { imageUrls, size = '3.5rem' } = props;

  return (
    <div css={list({ size, length: imageUrls.length })}>
      {imageUrls.slice(0, 3).map((url) => (
        <UserPreview key={url} imageUrl={url} size={size} />
      ))}
      {imageUrls.length > 3 && <Meatball />}
    </div>
  );
}
