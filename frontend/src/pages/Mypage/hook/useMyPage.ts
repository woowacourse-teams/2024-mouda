import useEditMyInfo from '@_hooks/mutaions/useEditMyInfo';
import useMyInfo from '@_hooks/queries/useMyInfo';
import { useState, useRef, useEffect, ChangeEvent } from 'react';
import { validateNickName } from '../validate';
import POLICES from '@_constants/poclies';
import heic2any from 'heic2any';
import loadingSpinner from '@_common/assets/loading.gif';

export default function useMyPage() {
  const { myInfo } = useMyInfo();
  const { mutate } = useEditMyInfo();
  const fileInput = useRef<HTMLInputElement | null>(null);
  const [profile, setProfile] = useState(myInfo?.profile || '');
  const [nickname, setNickname] = useState(myInfo?.nickname || '');
  const [description, setDescription] = useState(myInfo?.description || '');
  const [selectedFile, setSelectedFile] = useState<File | string>('');
  const [isEditing, setIsEditing] = useState(false);
  const [isReset, setIsReset] = useState('false');
  const [isShownRest, setIsShownRest] = useState(false);

  useEffect(() => {
    if (myInfo) {
      setNickname(myInfo.nickname || '');
      setDescription(myInfo.description || '');
      setProfile(myInfo.profile || '');
      setSelectedFile(myInfo.profile || '');
      myInfo.profile && setIsShownRest(true);
    }
  }, [myInfo]);

  const handleEditClick = () => {
    setIsEditing((prev) => !prev);
    setSelectedFile('');
  };
  const HEIC_SIGNATURE = '66747970'; // HEIC 매직 넘버

  const isHeifFile = (file: File): Promise<boolean> => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();

      reader.onloadend = () => {
        const arr = new Uint8Array(reader.result as ArrayBuffer).subarray(0, 8);
        let header = '';
        for (let i = 0; i < arr.length; i++) {
          header += arr[i].toString(16);
        }

        // HEIC 파일인지 확인
        const isHeif = header.includes(HEIC_SIGNATURE);
        resolve(isHeif);
      };

      reader.onerror = () => {
        reject(new Error('파일 읽기 중 오류 발생'));
      };

      reader.readAsArrayBuffer(file);
    });
  };

  /**
   * HEIC 파일을 JPEG로 변환
   * @param file
   * @returns
   */
  const handleFileConversion = async (file: File) => {
    try {
      const result = await heic2any({ blob: file, toType: 'image/jpeg' });

      const jpegBlob = Array.isArray(result)
        ? new Blob(result, { type: 'image/jpeg' })
        : result;

      const jpegFile = new File(
        [jpegBlob],
        file.name.replace(/\.[^/.]+$/, '.jpeg'),
        { type: 'image/jpeg' },
      );

      return jpegFile;
    } catch (error) {
      return null;
    }
  };

  const onChange = async (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      const maxSize = POLICES.maxProfileImageSize;
      const fileSize = e.target.files[0].size;
      if (fileSize > maxSize) {
        alert('파일첨부 사이즈는 5MB 이내로 가능합니다.');
        e.target.value = '';
        return;
      }

      let file = e.target.files[0];
      setProfile(loadingSpinner);

      const isHeif = await isHeifFile(file);

      if (isHeif) {
        try {
          const convertedFile = await handleFileConversion(file);
          if (!convertedFile) throw new Error('파일 변환 실패');
          setSelectedFile(convertedFile);
          file = convertedFile;
        } catch (error) {
          alert('이미지 파일을 처리하는 중 오류가 발생했습니다.');
          return;
        }
      } else {
        setSelectedFile(file);
      }

      setIsShownRest(true);
      setIsReset('false');

      const reader = new FileReader();
      reader.onload = () => {
        if (reader.readyState === 2 && typeof reader.result === 'string') {
          setProfile(reader.result);
        }
      };
      reader.onerror = () => {
        console.error('이미지 파일을 읽는 중 오류가 발생했습니다.');
        alert('이미지 파일을 읽는 중 오류가 발생했습니다.');
      };
      reader.readAsDataURL(file);
    } else {
      setProfile(myInfo?.profile ?? '');
      return;
    }
  };

  const onUpload = async () => {
    const formData = new FormData();

    // 파일 추가
    formData.append('file', selectedFile);

    // 문자열 데이터 추가
    formData.append('nickname', nickname ?? '');
    formData.append('description', description ?? '');
    formData.append('isReset', isReset);

    try {
      // 서버로 파일 및 데이터 전송
      mutate(formData); // FormData 객체 자체를 전달
      handleEditClick(); // 편집 모드 비활성화
    } catch (error) {
      console.error('파일 업로드 실패', error);
    }
  };

  const handleProfileClick = () => {
    fileInput.current?.click(); // ProfileFrame 클릭 시 파일 선택창 열기
  };

  const handleCancel = () => {
    // 편집 취소시 기존 데이터 복구
    setProfile(myInfo?.profile || '');
    setNickname(myInfo?.nickname || '');
    setDescription(myInfo?.description || '');
    setIsEditing(false);
    setIsReset('false');
    myInfo?.profile && setIsShownRest(false);
  };

  const handleDefaultProfile = () => {
    setProfile('');
    setSelectedFile('');
    setIsReset('true');
    setIsShownRest(false);
  };

  const isValidMyInfo = validateNickName(nickname);

  return {
    fileInput,
    myInfo,
    profile,
    nickname,
    description,
    selectedFile,
    isEditing,
    isReset,
    isShownRest,
    isValidMyInfo,
    setNickname,
    setDescription,
    setIsEditing,
    handleEditClick,
    onChange,
    onUpload,
    handleProfileClick,
    handleCancel,
    handleDefaultProfile,
  };
}
